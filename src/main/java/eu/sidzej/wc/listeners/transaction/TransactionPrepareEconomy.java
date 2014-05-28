package eu.sidzej.wc.listeners.transaction;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import eu.sidzej.wc.WCSign.e_type;
import eu.sidzej.wc.events.TransactionPrepareEvent;
import eu.sidzej.wc.events.TransactionPrepareEvent.e_states;
import eu.sidzej.wc.utils.Config;
import eu.sidzej.wc.utils.EconomyUtils;

public class TransactionPrepareEconomy implements Listener {
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void EconomyChecker(TransactionPrepareEvent e){
		if(e.isCancelled())
			return;
		if(e.getType().equals(e_type.BUY))
			if(EconomyUtils.getBalance(e.getPlayer()) > e.getSign().getBuyPrice()/Config.STACK_SIZE);
				e.setState(e_states.NOT_ENOUGH_MONEY);
	}

}
