package eu.sidzej.wc.listeners.sign;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import eu.sidzej.wc.events.SignCreationEvent;

public class SignCreationMonitor implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void MessageMonitor(SignCreationEvent e) {
		if (!e.isCancelled())
			return;

		String message = "";

		switch (e.getState()) {
			case BAD_NAME_LINE:
				message = "bad name";
				break;
			case BAD_TYPE_LINE:
				message = "bad type";
				break;
			case BAD_PRICE_LINE:
				message = "bad price";
				break;
			case BAD_ITEM_LINE:
				message = "bad item";
				break;
			case BAD_TYPE_TO_PRICE_LINES:
				message = "bad number type or prices";
				break;
			default:
				message = "general wtf";
				break;
		}
		
		e.getPlayer().sendMessage(message);

	}
}
