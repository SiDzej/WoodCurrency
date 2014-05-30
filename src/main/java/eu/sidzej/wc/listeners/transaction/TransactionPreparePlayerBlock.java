package eu.sidzej.wc.listeners.transaction;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import eu.sidzej.wc.PlayerManager;
import eu.sidzej.wc.events.TransactionPrepareEvent;
import eu.sidzej.wc.events.TransactionPrepareEvent.e_states;

public class TransactionPreparePlayerBlock implements Listener {
	
	@EventHandler(priority = EventPriority.HIGH)
	public void CheckPermission(TransactionPrepareEvent e){
		if(PlayerManager.getPlayerData(e.getPlayer()).isBlocked())
				e.setState(e_states.BLOCKED);
	}

}