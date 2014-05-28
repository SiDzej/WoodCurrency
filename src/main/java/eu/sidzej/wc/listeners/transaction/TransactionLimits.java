package eu.sidzej.wc.listeners.transaction;

import java.util.Calendar;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import eu.sidzej.wc.PlayerManager;
import eu.sidzej.wc.PlayerManager.PlayerData;
import eu.sidzej.wc.WCSign.e_type;
import eu.sidzej.wc.db.DBUtils;
import eu.sidzej.wc.events.TransactionEvent;

public class TransactionLimits implements Listener {

	@EventHandler(priority = EventPriority.LOWEST)
	public static void UpdatePlayerData(TransactionEvent e) {
		PlayerData data = PlayerManager.getPlayerData(e.getPlayer());

		Calendar c = (Calendar) Calendar.getInstance().clone();
		if (c.before(data.getDate()))
			return;
		data.getDate().add(Calendar.DATE, 1);
		if (c.before(data.getDate())) {
			data.incrementTier();
			data.resetDay();
		} else {
			data.decrementTier();
			data.resetDay();
		}
		DBUtils.UpdatePlayer(e.getPlayer().getUniqueId(), data);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public static void UdateLimits(TransactionEvent e) {
		PlayerData data = PlayerManager.getPlayerData(e.getPlayer());

		if (e.getType().equals(e_type.SELL))
			data.addCount(e.getFinalAmount());
	}
}
