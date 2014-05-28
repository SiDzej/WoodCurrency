package eu.sidzej.wc;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;

import eu.sidzej.wc.db.DBUtils;
import eu.sidzej.wc.utils.Config;

public class PlayerManager {
	private static final PlayerManager instance = new PlayerManager();
	private HashMap<UUID, PlayerData> players;

	private PlayerManager() {
		players = new HashMap<UUID, PlayerData>();
	}

	public static PlayerManager getInstance() {
		return instance;
	}

	public static PlayerData getPlayerData(UUID uuid) {
		if (instance.players.containsKey(uuid))
			return instance.players.get(uuid);
		else
			return DBUtils.getPlayerData(uuid);
	}
	
	public static PlayerData getPlayerData(Player player) {
		return getPlayerData(player.getUniqueId());
	}

	public static PlayerData addPlayer(UUID uuid, int id, int day, int total, byte tier,
			Timestamp timestamp) {
		PlayerData data = new PlayerData(id, day, total, tier, timestamp);
		instance.players.put(uuid, data);
		return data;
	}

	public static class PlayerData {
		private int id, day, total;
		private byte tier;
		private Timestamp timestamp;

		public PlayerData(int id, int day, int total, byte tier, Timestamp timestamp) {
			this.id = id;
			this.day = day;
			this.total = total;
			this.tier = tier;
			this.timestamp = timestamp;
		}

		public int getItemLeft() {
			return (int)(Config.MAX_SELL/Math.pow(2,tier-1) - day);
		}

		public void addCount(int amount) {
			day += amount;
			total += amount;
		}

		public int getID() {
			return id;
		}
	}
}
