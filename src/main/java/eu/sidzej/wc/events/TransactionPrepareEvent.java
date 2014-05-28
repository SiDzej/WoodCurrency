package eu.sidzej.wc.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.Action;

import eu.sidzej.wc.WCSign;
import eu.sidzej.wc.WCSign.e_type;

public class TransactionPrepareEvent extends Event {
	private static final HandlerList handlers = new HandlerList();
	private Player p;
	private WCSign s;
	private Action a;
	private e_states state = e_states.OK; 
	private e_type type = e_type.NONE;

	public TransactionPrepareEvent(Player p, WCSign sign, Action action) {
		this.p = p;
		this.s = sign;
		this.a = action;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
	
	public static enum e_states{
		OK,
		BAD_ACTION,
		NO_PERMISSIONS, 
		OTHER_ACTION,
	}
	
	public void setState(e_states s){
		state = s;
	}
	
	public e_states getState(){
		return state;
	}

	public boolean isCancelled() {
		return state != e_states.OK;
	}

	public Player getPlayer() {
		return this.p;
	}

	public WCSign getSign() {
		return s;
	}

	public Action getAction() {
		return a;
	}

	public void setType(e_type type) {
		this.type = type;
	}

	public e_type getType(){
		return type;
	}
}
