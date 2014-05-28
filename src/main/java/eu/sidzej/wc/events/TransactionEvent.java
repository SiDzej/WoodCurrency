package eu.sidzej.wc.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TransactionEvent extends Event {
	private static final HandlerList handlers = new HandlerList();

	public TransactionEvent(){
		
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
	
	

}
