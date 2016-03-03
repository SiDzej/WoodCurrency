package eu.sidzej.wc.db;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;

import com.google.common.io.CharStreams;

import eu.sidzej.wc.WoodCurrency;
import eu.sidzej.wc.config.Config;
import eu.sidzej.wc.utils.Log;

public class Database {

	private WoodCurrency plugin;
	private static ConnectionManager cm;
	public boolean valid = false;

	public byte current_version = 3;

	private List<String> table_names = Arrays.asList("wc_players",
			"wc_transactions", "wc_signs", "wc_info");

	public Database(WoodCurrency plugin) throws ClassNotFoundException {
		this.plugin = plugin;

		cm = new ConnectionManager(Config.host, Config.port, Config.database,
				Config.user, Config.pass);
		TimedConnection c = cm.getConnection();
		if (c == null || !c.isValid())
			return;
		c.release();

		Iterator<String> iterator = table_names.iterator();
		while (iterator.hasNext())
			if (!tableExists(iterator.next())) {
				if (!createDBTables())
					return;
				break;
			}

		valid = true;
		update();
	}

	public static TimedConnection getConnection() {
		return cm.getConnection();
	}

	public void close() {
		if (valid)
			cm.close();
	}

	// / Private part
	private boolean createDBTables() {
		Log.info("Creating DB tables structure...");
		TimedConnection c = null;
		Statement s = null;
		try {
			c = cm.getConnection();
			s = c.createStatement();
			if (plugin.getResource("CREATE.sql") == null) {
				plugin.disable("No CREATE.sql file found! Critical error.");
				return false;
			}
			String[] queries = CharStreams.toString(
					new InputStreamReader(plugin.getResource("CREATE.sql")))
					.split(";");
			for (String query : queries) {
				Log.debug("Creating DB table");
				s.execute(query);
			}
		} catch (SQLException | IOException e) {
			Log.error(e.getMessage());
			plugin.disable("Can't create database.");
			return false;
		} finally {
			try {
				if (s != null)
					s.close();
				c.release();
			} catch (SQLException e) {
				Log.error("Unable to close connection.");
			}
		}
		return true;
	}

	private boolean tableExists(String table) {
		TimedConnection c = null;
		Statement s = null;
		try {
			c = cm.getConnection();
			s = c.createStatement();

			ResultSet res = c.getMetaData().getTables(null, null, table, null);
			if (res.next())
				return true;
			else
				return false;
		} catch (SQLException e) {
			Log.error(e.getMessage());
			plugin.disable("Can't reach database.");
			return false;
		} finally {
			try {
				if (s != null)
					s.close();
				c.release();
			} catch (SQLException e) {
				Log.error("Unable to close connection.");
			}
		}
	}

	private void update() {
		TimedConnection c = null;
		Statement s = null;
		try {
			c = cm.getConnection();
			s = c.createStatement();

			ResultSet res = s
					.executeQuery("SELECT version FROM wc_info WHERE id=1");
			byte version;
			if(res.next())
				version = res.getByte("version");
			else 
				version = 3;
			
			version++;
			
			switch (version) {
			case 1:
				s.execute("INSERT INTO wc_info (version) VALUES (\"1\")");
				
			case 2:
				update2(c);
			case 3:
			    update3(c);
			default:
				break;
			}
			s.execute("UPDATE wc_info SET version = \""+ current_version +"\"");
		} catch (SQLException e) {
			Log.error(e.getMessage());
			plugin.disable("Can't reach database.");
		} finally {
			try {
				if (s != null)
					s.close();
				c.release();
			} catch (SQLException e) {
				Log.error("Unable to close connection.");
			}
		}

	}
	
	private void update3(TimedConnection c) throws SQLException{
	    Log.info("Updating database to version 3");
	    Statement s = c.createStatement();
	    // buy sell enum to readable DB form
	    s.execute("ALTER TABLE wc_transactions ADD type1 enum('sell','buy')");
	    s.execute("UPDATE wc_transactions SET type1=1 WHERE type=1");
	    s.execute("UPDATE wc_transactions SET type1=2 WHERE type=0");
	    s.execute("ALTER TABLE wc_transactions DROP type");
	    s.execute("ALTER TABLE wc_transactions change type1 type enum('sell','buy')");
	    // tree species to readable DB form
	    s.execute("ALTER TABLE wc_transactions CHANGE block block enum('oak','birch','spruce','jungle','dark','acacia')");	
	    // resolve uuids to current nick
	    ResultSet res = s.executeQuery("SELECT id,uuid FROM wc_players");
        while (res.next()){
            Statement update = c.createStatement();
            String nick = Bukkit.getOfflinePlayer(UUID.fromString(res.getString("uuid")))
                    .getName();
            update.execute("UPDATE wc_players SET nick = \""+nick+"\" WHERE id=\""+res.getInt("id")+"\"");
        }
	}
	
	private void update2(TimedConnection c) throws SQLException{
		Log.info("Updating database to version 2");
		Statement s = c.createStatement();
		s.execute("ALTER TABLE wc_players ADD nick char(36) NULL");
		s = c.createStatement();
		ResultSet res = s.executeQuery("SELECT id,uuid FROM wc_players");
		while (res.next()){
			Statement update = c.createStatement();
			String nick = Bukkit.getOfflinePlayer(UUID.fromString(res.getString("uuid")))
					.getName();
			update.execute("UPDATE wc_players SET nick = \""+nick+"\" WHERE id=\""+res.getInt("id")+"\"");
		}
		
	}
}
