package eu.sidzej.wc.listeners.transaction;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import eu.sidzej.wc.events.TransactionPrepareEvent;

public class TransactionPrepareMonitor implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void MessageMonitor(TransactionPrepareEvent e) {
		if (!e.isCancelled())
			return;

		String message = "";

		switch (e.getState()) {
			case BAD_ACTION:
				break;
			case OTHER_ACTION:
			case TOO_FAST_CLICK:
				break;
			case NO_PERMISSIONS:
				message = "no permission";
				break;
			case NO_ITEM_TO_SELL:
				message = "no item";
				break;
			case NO_SPACE_IN_INVENTORY:
				message = "no space";
				break;
			case NOT_ENOUGH_MONEY:
				message = "no money";
				break;
			default:
				message = "general wtf";
				break;
		}

		if (!message.isEmpty())
			e.getPlayer().sendMessage(message);
	}
}
