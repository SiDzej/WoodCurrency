package eu.sidzej.wc.utils;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class PlayerUtils {

	@SuppressWarnings("deprecation")
	public static OfflinePlayer getOfflinePlayer(String name) {
		return Bukkit.getOfflinePlayer(name);// TODO do'h wtf
	}

}
