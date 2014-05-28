package eu.sidzej.wc.listeners.transaction;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import eu.sidzej.wc.WCSign.e_type;
import eu.sidzej.wc.events.TransactionEvent;
import eu.sidzej.wc.utils.EconomyUtils;

public class TransactionMessage implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public static void SendMsg(TransactionEvent e) {
		if (e.getType().equals(e_type.BUY))
			e.getPlayer().sendMessage(
					"You just bought " + e.getFinalAmount() + " pieces of " + e.getItemName()
							+ " for " + EconomyUtils.format(e.getFinalPrice()) + ".");
		if (e.getType().equals(e_type.SELL))
			e.getPlayer().sendMessage(
					"You just sold " + e.getFinalAmount() + " pieces of " + e.getItemName()
							+ " for " + EconomyUtils.format(e.getFinalPrice()) + ".");
	}

}
