package eu.sidzej.wc.listeners.transaction;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import eu.sidzej.wc.events.TransactionPrepareEvent;

public class TransactionPrepareMonitor implements Listener{

	@EventHandler(priority = EventPriority.MONITOR)
	public void MessageMonitor(TransactionPrepareEvent e) {
		if (!e.isCancelled())
			return;

		String message = "";

		switch (e.getState()) {
			case BAD_ACTION:
				break;
			case OTHER_ACTION:
				break;
			case NO_PERMISSIONS:
				message = "no permission";
				break;
			default:
				message = "general wtf";
				break;
		}
		
		if(!message.isEmpty())
			e.getPlayer().sendMessage(message);
	}
}
