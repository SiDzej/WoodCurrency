package eu.sidzej.wc.listeners.transaction;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import eu.sidzej.wc.WCSign.e_type;
import eu.sidzej.wc.events.TransactionEvent;
import eu.sidzej.wc.utils.EconomyUtils;

public class TransactionEconomy implements Listener {
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void BuyListener(TransactionEvent e){
		if(!e.getType().equals(e_type.BUY))
			return;	
		
		double price = e.getPrice() * e.getFinalAmount();
		EconomyUtils.withdraw(e.getPlayer(),price);
		
		e.setFinalPrice(price);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void SellListener(TransactionEvent e){
		if(!e.getType().equals(e_type.SELL))
			return;
		double price = e.getPrice() * e.getFinalAmount();
		EconomyUtils.deposit(e.getPlayer(),price);
		
		e.setFinalPrice(price);
	}
}
