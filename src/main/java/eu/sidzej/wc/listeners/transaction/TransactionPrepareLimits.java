package eu.sidzej.wc.listeners.transaction;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import eu.sidzej.wc.PlayerManager;
import eu.sidzej.wc.PlayerManager.PlayerData;
import eu.sidzej.wc.events.TransactionPrepareEvent;
import eu.sidzej.wc.events.TransactionPrepareEvent.e_states;

public class TransactionPrepareLimits implements Listener {
	
	@EventHandler(priority = EventPriority.NORMAL)
	public static void TransactionsLimits(TransactionPrepareEvent e){
		PlayerData data = PlayerManager.getPlayerData(e.getPlayer());
		if(!(data.getItemLeft() > 0))
			e.setState(e_states.DAY_LIMIT);
	}

}
