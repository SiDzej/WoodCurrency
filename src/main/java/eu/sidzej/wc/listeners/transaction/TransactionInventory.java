package eu.sidzej.wc.listeners.transaction;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import eu.sidzej.wc.PlayerManager;
import eu.sidzej.wc.PlayerManager.PlayerData;
import eu.sidzej.wc.WCSign.e_type;
import eu.sidzej.wc.events.TransactionEvent;
import eu.sidzej.wc.inventory.WCInventory;
import eu.sidzej.wc.utils.Config;
import eu.sidzej.wc.utils.EconomyUtils;

public class TransactionInventory implements Listener {
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void GetAmountSellListener(TransactionEvent e){
		if(!e.getType().equals(e_type.SELL))
			return;
		WCInventory inv = new WCInventory(e.getPlayer());
		PlayerData data = PlayerManager.getPlayerData(e.getPlayer());
		int amount = Config.STACK_SIZE - inv.removeItems(e.getItemStack(), Config.STACK_SIZE);
		
		if(data.getItemLeft() < amount){
			inv.addItems(e.getItemStack(), amount - data.getItemLeft());
			amount -= (amount - data.getItemLeft());
		}
		
		e.setFinalAmount(amount);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void GetAmountBuyListener(TransactionEvent e){
		if(!e.getType().equals(e_type.BUY))
			return;
		double money = EconomyUtils.getBalance(e.getPlayer());
		WCInventory inv = new WCInventory(e.getPlayer());
		int amount = Config.STACK_SIZE - inv.addItems(e.getItemStack(), Config.STACK_SIZE);
		if(amount*e.getPrice() > money){
			inv.removeItems(e.getItemStack(), (int)(money/e.getPrice() * amount));
			amount = (int)(money/e.getPrice() * amount);
		}
		
		e.setFinalAmount(amount);
	}
}
