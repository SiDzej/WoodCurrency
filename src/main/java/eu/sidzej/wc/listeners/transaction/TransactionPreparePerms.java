package eu.sidzej.wc.listeners.transaction;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import eu.sidzej.wc.WCSign.e_type;
import eu.sidzej.wc.events.TransactionPrepareEvent;
import eu.sidzej.wc.events.TransactionPrepareEvent.e_states;

public class TransactionPreparePerms implements Listener {
	
	@EventHandler(priority = EventPriority.HIGH)
	public void CheckPermission(TransactionPrepareEvent e){
		if(e.getType().equals(e_type.BUY))
			if(!e.getPlayer().hasPermission("woodcurrency.buy"))
				e.setState(e_states.NO_PERMISSIONS);
		if(e.getType().equals(e_type.SELL))
			if(!e.getPlayer().hasPermission("woodcurrency.sell"))
				e.setState(e_states.NO_PERMISSIONS);
	}

}
