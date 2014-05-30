package eu.sidzej.wc.utils;

import java.util.Calendar;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import eu.sidzej.wc.PlayerManager.PlayerData;
import eu.sidzej.wc.db.DBUtils;

public class PlayerUtils {

	@SuppressWarnings("deprecation")
	public static OfflinePlayer getOfflinePlayer(String name) {
		return Bukkit.getOfflinePlayer(name);// TODO do'h wtf
	}

	/**
	 * 
	 * Check and update player limits
	 * 
	 * @param PlayerData
	 * @return true - limits changed, false nothing changed
	 */
	public static boolean checkDailyLimits(PlayerData data, boolean tier) {
		Calendar c = (Calendar) Calendar.getInstance().clone();
		if (c.before(data.getDate()))
			return false;
		if (tier) {
			data.getDate().add(Calendar.DATE, 1);
			if (c.before(data.getDate())) {
				data.incrementTier();
			} else {
				data.decrementTier();
				while (data.getTier() > 1) {
					data.getDate().add(Calendar.DATE, 1);
					if (c.after(data.getDate()))
						data.decrementTier();
					else
						break;
				}
				if (data.getTier() == 1) {
					c.add(Calendar.DATE, 1);
					data.setDate(c);
				}
			}
		}
		data.resetDay();

		DBUtils.UpdatePlayer(data);
		return true;
	}
}
