package eu.sidzej.wc.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import eu.sidzej.wc.PlayerManager;
import eu.sidzej.wc.PlayerManager.PlayerData;
import eu.sidzej.wc.ProtectionManager;
import eu.sidzej.wc.WCSign.e_type;
import static eu.sidzej.wc.WCSign.e_type.SELL;
import eu.sidzej.wc.utils.Log;
import eu.sidzej.wc.utils.TimeUtils;

public class DBUtils {

	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static boolean registerTransaction(int id, int block, int count, e_type e_type,
			double price) {
		TimedConnection c = null;
		Statement s = null;
		String time = sdf.format(new Date());
		try {
			c = Database.getConnection();
			s = c.createStatement();
			s.execute("INSERT INTO wc_transactions (player,block,count,date,type,price) VALUES (\""
					+ id + "\",\"" + block + "\",\"" + count + "\",\"" + time + "\",\""
					+ ((e_type.equals(SELL)) ? 1 : 0) + "\",\"" + price + "\")");
		} catch (SQLException ex) {
			Log.error(ex.getMessage());
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
	 * 
	 * @param l
	 *            - shop location
	 * @param d
	 *            - sing attached direction
	 * @return true if successfully saved to DB
	 */
	public static boolean registerShop(Location l, int d) {
		TimedConnection c = null;
		Statement s = null;
		try {
			c = Database.getConnection();
			s = c.createStatement();
			s.execute("INSERT INTO wc_signs (x,y,z,world,direction) VALUES (\"" + l.getBlockX()
					+ "\",\"" + l.getBlockY() + "\",\"" + l.getBlockZ() + "\",\""
					+ l.getWorld().getName() + "\",\"" + d + "\")");
		} catch (SQLException ex) {
			Log.error("Unable to put new shop into DB.");
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

	public static boolean registerPlayer(UUID uuid) {
		TimedConnection c = null;
		Statement s = null;
		try {
			c = Database.getConnection();
			s = c.createStatement();
			s.execute("INSERT INTO wc_players (uuid,timestamp) VALUES (\"" + uuid + "\",\""
					+ TimeUtils.getTime() + "\")");
		} catch (SQLException ex) {
			Log.error("Unable to put new player into DB.");
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

	public static PlayerData getPlayerData(UUID uuid) {
		TimedConnection c = null;
		Statement s = null;
		PlayerData data = null;
		try {
			c = Database.getConnection();
			s = c.createStatement();
			ResultSet set = s.executeQuery("SELECT * FROM wc_players WHERE uuid = \""
					+ uuid.toString() + "\"");
			if (set.next())
				data = new PlayerData(uuid, set.getInt("id"), set.getInt("day"),
						set.getInt("totalbuy"), set.getInt("totalsell"), set.getByte("tier"),
						set.getTimestamp("timestamp"), set.getBoolean("blocked"));
		} catch (SQLException e) {
			Log.error(e.getMessage());
			Log.error("Unable to get player data.");
			return null;
		} finally {
			try {
				if (s != null)
					s.close();
				c.release();
			} catch (SQLException e) {
				Log.error("Unable to close connection.");
			}
		}
		return data;
	}

	public static boolean loadOnlinePlayers() {
		TimedConnection c = null;
		Statement s = null;
		try {
			c = Database.getConnection();
			s = c.createStatement();
			ResultSet set = s.executeQuery("SELECT * FROM wc_players");
			List<Player> playerlist = new ArrayList<Player>(
					Arrays.asList(Bukkit.getOnlinePlayers()));
			List<UUID> players = new ArrayList<UUID>();
			for (Player p : playerlist) {
				players.add(p.getUniqueId());
			}
			while (set.next()) {
				UUID uuid = UUID.fromString(set.getString("uuid"));
				if (players.contains(uuid))
					PlayerManager.addPlayer(uuid, set.getInt("id"), set.getInt("day"),
							set.getInt("totalbuy"), set.getInt("totalsell"), set.getByte("tier"),
							set.getTimestamp("timestamp"), set.getBoolean("blocked"));
			}
		} catch (SQLException e) {
			Log.error(e.getMessage());
			Log.error("Unable to load online players.");
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
	 * 
	 * @return true if shops are loaded successfully
	 */
	public static boolean loadShops() {
		TimedConnection c = null;
		Statement s = null;
		try {
			c = Database.getConnection();
			s = c.createStatement();
			ResultSet set = s.executeQuery("SELECT * FROM wc_signs");
			while (set.next()) {
				Location l = new Location(Bukkit.getServer().getWorld(set.getString("world")),
						set.getDouble("x"), set.getDouble("y"), set.getDouble("z"));
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
	 * 
	 * @param l
	 *            - shop location
	 * @return true if successfully removed else false
	 */
	public static boolean removeShop(Location l) {
		TimedConnection c = null;
		Statement s = null;
		try {
			c = Database.getConnection();
			s = c.createStatement();

			s.execute("DELETE FROM wc_signs WHERE x=\"" + l.getBlockX() + "\" AND y=\""
					+ l.getBlockY() + "\" AND z=\"" + l.getBlockZ() + "\" AND world=\""
					+ l.getWorld().getName() + "\"");
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

	public static void UpdatePlayer(PlayerData data) {
		TimedConnection c = null;
		Statement s = null;
		try {
			c = Database.getConnection();
			s = c.createStatement();
			s.execute("UPDATE wc_players SET day = '" + data.getDay() + "',tier = '"
					+ data.getTier() + "',timestamp = '" + data.getTimestamp() + "', totalbuy = '"
					+ data.getTotalBuy() + "', totalsell = '" + data.getTotalSell()
					+ "', blocked = '" + ((data.isBlocked()) ? 1 : 0) + "' WHERE `id` = '"
					+ data.getID() + "'");
		} catch (SQLException ex) {
			Log.error(ex.getMessage());
			Log.error("Unable to update player.");
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

	public static boolean UpdatePlayerBan(UUID uuid, boolean b) {
		TimedConnection c = null;
		Statement s = null;
		if (getPlayerData(uuid) == null)
			return false;
		try {
			c = Database.getConnection();
			s = c.createStatement();
			s.execute("UPDATE wc_players SET blocked = '" + ((b) ? 1 : 0) + "' WHERE `uuid` = '"
					+ uuid + "'");
		} catch (SQLException ex) {
			Log.error(ex.getMessage());
			Log.error("Unable to update player.");
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

	public static List<String> getTopTen() {
		TimedConnection c = null;
		Statement s = null;
		List<String> data = new ArrayList<String>();
		try {
			c = Database.getConnection();
			s = c.createStatement();
			ResultSet set = s
					.executeQuery("SELECT uuid,totalsell FROM `wc_players` ORDER BY totalsell DESC LIMIT 10");
			int i = 1;
			while (set.next()) {
				data.add("" + ChatColor.GRAY + (i++) + ": " + ChatColor.GREEN
						+ Bukkit.getOfflinePlayer(UUID.fromString(set.getString("uuid"))).getName()
						+ ChatColor.GRAY + " - " + ChatColor.GREEN + set.getString("totalsell"));
			}
		} catch (SQLException e) {
			Log.error(e.getMessage());
			Log.error("Unable to get player data.");
			return null;
		} finally {
			try {
				if (s != null)
					s.close();
				c.release();
			} catch (SQLException e) {
				Log.error("Unable to close connection.");
			}
		}
		return data;
	}

	public static void updateShop(Location l, int x, int y) {
		TimedConnection c = null;
		Statement s = null;
		try {
			c = Database.getConnection();
			s = c.createStatement();
			s.execute("UPDATE wc_signs SET sells = sells + " + y + ", buys = buys + " + x
					+ " WHERE x=\"" + l.getBlockX() + "\" AND y=\"" + l.getBlockY() + "\" AND z=\""
					+ l.getBlockZ() + "\" AND world=\"" + l.getWorld().getName() + "\"");
		} catch (SQLException ex) {
			Log.error(ex.getMessage());
			Log.error("Unable to update shops in DB.");
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
	
	public static List<String> getBannedPlayers(){
		TimedConnection c = null;
		Statement s = null;
		List<String> data = new ArrayList<String>();
		try {
			c = Database.getConnection();
			s = c.createStatement();
			ResultSet set = s
					.executeQuery("SELECT uuid FROM `wc_players` WHERE blocked = 1");
			while (set.next()) {
				data.add((Bukkit.getOfflinePlayer(UUID.fromString(set.getString("uuid"))).getName()));
			}
		} catch (SQLException e) {
			Log.error(e.getMessage());
			Log.error("Unable to get player data.");
			return null;
		} finally {
			try {
				if (s != null)
					s.close();
				c.release();
			} catch (SQLException e) {
				Log.error("Unable to close connection.");
			}
		}
		return data;
	}
	
}
