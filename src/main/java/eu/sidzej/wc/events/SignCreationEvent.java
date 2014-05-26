package eu.sidzej.wc.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SignCreationEvent extends Event{
	private static final HandlerList handlers = new HandlerList();
	private Player p;
	private String[] lines;
	private boolean two_prices = false;
	private E_States state = E_States.OK;

	public SignCreationEvent(Player player, String[] lines) {
		p = player;
		this.lines = lines;
	}

	public boolean isCancelled() {
		return state != E_States.OK;
	}

	public String getSignLine(byte i) {
		return lines[i];
	}

	public Player getPlayer() {
		return p;
	}

	public String[] getSignLines() {
		return lines;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	public void setLine(byte i, String s) {
		lines[i] = s;
	}

	public void setTwoPrices(boolean b) {
		two_prices = b;
	}

	public boolean getTwoPrices() {
		return two_prices;
	}
	
	public static enum E_States{
		OK,
		BAD_NAME_LINE,
		BAD_TYPE_LINE,
		BAD_PRICE_LINE,
		BAD_ITEM_LINE,
		BAD_TYPE_TO_PRICE_LINES,
	}

	public E_States getState() {
		return state;
	}
	
	public void setState(E_States s){
		state = s;
	}
}
