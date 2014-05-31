package eu.sidzej.wc.listeners.transaction;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;

import eu.sidzej.wc.WCSign.e_type;
import eu.sidzej.wc.events.TransactionPrepareEvent;
import eu.sidzej.wc.events.TransactionPrepareEvent.e_states;

public class TransactionPrepareAction implements Listener {

	@EventHandler(priority = EventPriority.LOW)
	public void ActionChecker(TransactionPrepareEvent e) {
		Action a = e.getAction();
		e_type type = e_type.NONE;
		if (a.equals(Action.LEFT_CLICK_AIR) || a.equals(Action.LEFT_CLICK_BLOCK))
			type = e_type.SELL;
		else if (a.equals(Action.RIGHT_CLICK_AIR) || a.equals(Action.RIGHT_CLICK_BLOCK))
			type = e_type.BUY;
		else {
			e.setState(e_states.BAD_ACTION);
			return;
		}

		if (e.getSign().getType().equals(type) || e.getSign().getType().equals(e_type.BUYSELL))
			e.setType(type);
		else
			e.setState(e_states.OTHER_ACTION);
	}

}
