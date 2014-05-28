package eu.sidzej.wc.listeners.transaction;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import eu.sidzej.wc.events.TransactionPrepareEvent;
import eu.sidzej.wc.events.TransactionPrepareEvent.e_states;

public class TransactionPreparePerms implements Listener {
	
	@EventHandler(priority = EventPriority.LOW)
	public void CheckPermission(TransactionPrepareEvent e){
		if(!e.getPlayer().hasPermission("woodcurrency.buysell"))
			e.setState(e_states.NO_PERMISSIONS);
	}

}
