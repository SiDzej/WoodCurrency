package eu.sidzej.wc.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import eu.sidzej.wc.ProtectionManager;
import eu.sidzej.wc.utils.Log;

public class DBUtils {
	
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static boolean registerTransaction(int player, int block, int count,int type) {
		TimedConnection c = null;
		Statement s = null;
		try {	
			String time = sdf.format(new Date());
			
			c = Database.getConnection();
			s = c.createStatement();
			s.execute("INSERT INTO wc_transactions (player,block,count,date,type) VALUES (\""
					+ player + "\",\"" + block + "\",\"" + count + "\",\"" 
					+ time + "\",\"" + type +"\")");
		} catch (SQLException ex) {
			Log.error("Unable to log transaction to DB.");
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
	
	
	/**
	 * Register shop to database, so it can be loaded after restart
	 * @param l - shop location
	 * @param d - sing attached direction
	 * @return true if successfully saved to DB
	 */
	public static boolean registerShop(Location l, int d) {
		TimedConnection c = null;
		Statement s = null;
		try {
			c = Database.getConnection();
			s = c.createStatement();
			s.execute("INSERT INTO wc_signs (x,y,z,world,direction) VALUES (\""
					+ l.getBlockX() + "\",\"" + l.getBlockY() + "\",\"" + l.getBlockZ() + "\",\"" 
					+ l.getWorld().getName() + "\",\"" + d + "\")");
		} catch (SQLException ex) {
			Log.error("Unable to new shop to DB.");
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
	
	/**
	 * Load all shops from DB - used on plugin load
	 * @return true if shops are loaded successfully
	 */
	public static boolean loadShops() {
		TimedConnection c = null;
		Statement s = null;
		try {
			c = Database.getConnection();
			s = c.createStatement();
			ResultSet set = s.executeQuery("SELECT * FROM wc_signs");
			while(set.next()){
				Location l = new Location(Bukkit.getServer().getWorld(set.getString("world")),
						set.getDouble("x"),set.getDouble("y"),set.getDouble("z"));
				ProtectionManager.addFromDB(l, set.getInt("direction"));
			}

		} catch (SQLException e) {
			Log.error(e.getMessage());
			Log.error("Unable to get points.");
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
		Log.info("WC Shops loaded succefully.");
		return true;
	}

	/**
	 * Remove given shop location from DB
	 * @param l - shop location
	 * @return true if successfully removed else false
	 */
	public static boolean removeShop(Location l) {
		TimedConnection c = null;
		Statement s = null;
		try {
			c = Database.getConnection();
			s = c.createStatement();
			
			s.execute("DELETE FROM wc_signs WHERE x=\"" + l.getBlockX() + "\" AND y=\"" + 
					l.getBlockY() + "\" AND z=\"" + l.getBlockZ() + "\"");
		} catch (SQLException ex) {
			Log.error("Unable to remove shop sign from DB.");
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
}
