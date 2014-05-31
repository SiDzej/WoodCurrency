package eu.sidzej.wc.listeners.transaction;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import eu.sidzej.wc.config.Lang;
import eu.sidzej.wc.events.TransactionPrepareEvent;
import eu.sidzej.wc.utils.Log;

public class TransactionPrepareMonitor implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void MessageMonitor(TransactionPrepareEvent e) {
		if (!e.isCancelled())
			return;
		Location l = e.getSign().getLocation();
		String message = "";

		switch (e.getState()) {
			case BAD_ACTION:
			case OTHER_ACTION:
			case TOO_FAST_CLICK:
				break;
			case BROKEN_SIGN:
				message = Lang.FAKE_SIGN;
				Log.error("Fake shop at "  + l.getWorld().getName() + "," + l.getX() + "," + l.getY()
				+ "," + l.getZ() + " found by " + e.getPlayer().getName() + ".");
				break;
			case BLOCKED:
				message = Lang.PLAYER_BANNED;
				break;
			case DAY_LIMIT:
				message = Lang.DAY_LIMIT_REACHED;
				break;
			case NO_PERMISSIONS:
				message = Lang.NO_PERMISSION;
				break;
			case NO_ITEM_TO_SELL:
				message = Lang.NO_REQUIRED_ITEM;
				break;
			case NO_SPACE_IN_INVENTORY:
				message = Lang.NO_SPACE_IN_INVENTORY;
				break;
			case NOT_ENOUGH_MONEY:
				message = Lang.NOT_ENOUGH_MONEY;
				break;
			default:
				message = Lang.GENERAL_PROBLEM;
				break;
		}

		if (!message.isEmpty())
			e.getPlayer().sendMessage(message);
	}
}
