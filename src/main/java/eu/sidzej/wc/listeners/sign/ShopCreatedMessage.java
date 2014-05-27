package eu.sidzej.wc.listeners.sign;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import eu.sidzej.wc.events.ShopCreatedEvent;

public class ShopCreatedMessage implements Listener {
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void ShopCreatedMSG(ShopCreatedEvent e){
		e.getPlayer().sendMessage("WoodCurrency shop created. Thank you.");
	}

}
