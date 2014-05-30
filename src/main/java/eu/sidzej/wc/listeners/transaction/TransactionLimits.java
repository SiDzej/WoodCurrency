package eu.sidzej.wc.listeners.transaction;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import eu.sidzej.wc.PlayerManager;
import eu.sidzej.wc.PlayerManager.PlayerData;
import eu.sidzej.wc.WCSign.e_type;
import eu.sidzej.wc.events.TransactionEvent;

public class TransactionLimits implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public static void UdateLimits(TransactionEvent e) {
		PlayerData data = PlayerManager.getPlayerData(e.getPlayer());

		if (e.getType().equals(e_type.SELL))
			data.addCountSell(e.getFinalAmount());
		if (e.getType().equals(e_type.BUY))
			data.addCountBuy(e.getFinalAmount());
	}
}
