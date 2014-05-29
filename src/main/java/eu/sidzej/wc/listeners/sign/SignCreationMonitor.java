package eu.sidzej.wc.listeners.sign;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import eu.sidzej.wc.config.Lang;
import eu.sidzej.wc.events.SignCreationEvent;

public class SignCreationMonitor implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void MessageMonitor(SignCreationEvent e) {
		if (!e.isCancelled())
			return;

		String message = "";

		switch (e.getState()) {
			case BAD_NAME_LINE:
				message = Lang.BAD_FIRST_LINE;
				break;
			case BAD_TYPE_LINE:
				message = Lang.BAD_SECOND_LINE;
				break;
			case BAD_PRICE_LINE:
				message = Lang.BAD_THIRD_LINE;
				break;
			case BAD_ITEM_LINE:
				message = Lang.BAD_FOURTH_LINE;
				break;
			case BAD_TYPE_TO_PRICE_LINES:
				message = Lang.WRONG_NUMBER_OF_TYPES_TO_PRICES;
				break;
			case NO_PERMISSIONS:
				message = Lang.NO_PERMISSION;
				break;
			default:
				message = Lang.GENERAL_PROBLEM;
				break;
		}
		
		e.getPlayer().sendMessage(message);

	}
}
