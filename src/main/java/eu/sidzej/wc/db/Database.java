package eu.sidzej.wc.db;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import com.google.common.io.CharStreams;

import eu.sidzej.wc.WoodCurrency;
import eu.sidzej.wc.utils.Config;
import eu.sidzej.wc.utils.Log;

public class Database {

	private WoodCurrency plugin;
	private static ConnectionManager cm;
	public boolean valid = false;

	private List<String> table_names = Arrays.asList("wc_players", "wc_transactions",
			"wc_signs");

	public Database(WoodCurrency plugin) throws ClassNotFoundException {
		this.plugin = plugin;

		cm = new ConnectionManager(Config.host, Config.port, Config.database, Config.user,
				Config.pass);
		TimedConnection c = cm.getConnection();
		if(c==null || !c.isValid())
			return;
		c.release();
		
		Iterator<String> iterator = table_names.iterator();
		while (iterator.hasNext())
			if (!tableExists(iterator.next())) {
				if(!createDBTables())
					return;
				break;
			}
		
		valid = true;
	}
	
	public static TimedConnection getConnection(){
		return cm.getConnection();
	}

	public boolean setPassword(String p, String pass) {
		TimedConnection c = null;
		Statement s = null;
		String password = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(pass.getBytes("UTF-8"));
			byte[] digest = md.digest();
			password = DatatypeConverter.printHexBinary(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		try {
			c = cm.getConnection();
			s = c.createStatement();
			s.execute("INSERT INTO ma_players (nick,password) VALUES (\"" + p + "\",\""
					+ password + "\") ON DUPLICATE KEY UPDATE password=\""+password+"\"");
			Log.debug("Setting " + p + " password");
			
		} catch (SQLException e) {
			Log.error(e.getMessage());
			Log.error("Unable to set password.");
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
	
	public void close() {
		if(valid)
			cm.close();
	}
	
	/// Private part
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
					new InputStreamReader(plugin.getResource("CREATE.sql"))).split(";");
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
}
