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

		if (tier || (data.getDay() <= (data.getDayLimit() * 0.3))) {
			if (data.getTier() == 0) // first day action - double items
				data.incrementTier();
			data.getDate().add(Calendar.DATE, 1);
			if (c.before(data.getDate()) && (data.getDay() > data.getDayLimit() * 0.3)) {
				data.incrementTier();
			} else {
				data.decrementTier();
				while (data.getTier() > 1) {
					if (!c.before(data.getDate()))
						data.getDate().add(Calendar.DATE, 1);
					if (c.after(data.getDate()))
						data.decrementTier();
					else
						break;
				}
				if (data.getTier() == 1) {
					data.setDate(((Calendar)c.clone()));
					data.getDate().add(Calendar.DATE, 1);
				}
			}
		}
		if (c.after(data.getDate())) {
			if(data.getDay() > data.getDayLimit() * 0.3)
				data.incrementTier();
			data.getDate().add(Calendar.DATE, 1);
			if (data.getTier() == 0) // first day action - double items
				data.incrementTier();
			data.resetDay();
			checkDailyLimits(data,false);
		}
		data.resetDay();

		DBUtils.UpdatePlayer(data);
		return true;
	}
}
