package eu.sidzej.wc.listeners.transaction;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import eu.sidzej.wc.WCSign.e_type;
import eu.sidzej.wc.events.TransactionPrepareEvent;
import eu.sidzej.wc.events.TransactionPrepareEvent.e_states;
import eu.sidzej.wc.inventory.WCInventory;

public class TransactionPrepareInventory implements Listener {
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void InventoryChecker(TransactionPrepareEvent e){
		if(e.isCancelled())
			return;
		if(e.getSign().getItem() == null){
			e.setState(e_states.BROKEN_SIGN);
			return;
		}

		WCInventory inv = new WCInventory(e.getPlayer());
		if(e.getType().equals(e_type.SELL)){
			if(!inv.hasItemStack(e.getSign().getItem()))
				e.setState(e_states.NO_ITEM_TO_SELL);
		}
		if(e.getType().equals(e_type.BUY))
			if(!inv.hasEmptySpaceFor(e.getSign().getItem()))
				e.setState(e_states.NO_SPACE_IN_INVENTORY);
	}

}
