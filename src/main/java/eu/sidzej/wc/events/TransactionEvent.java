package eu.sidzej.wc.events;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import eu.sidzej.wc.WCSign;
import eu.sidzej.wc.WCSign.e_type;
import eu.sidzej.wc.config.Config;

public class TransactionEvent extends Event {
	private static final HandlerList handlers = new HandlerList();
	private Player p;
	private double price;
	private WCSign s;
	private e_type type;
	private Location loc;
	
	private double finalprice = 0.0;
	private int finalamount = 0;
	

	public TransactionEvent(Player p, e_type type, WCSign s, Location l){
		this.p = p;
		this.s = s;
		this.type = type;
		this.price = (type.equals(e_type.BUY))?s.getBuyPrice():s.getSellPrice();
		this.loc = l;
		
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	public Player getPlayer() {
		return p;
	}

	public ItemStack getItemStack() {
		return s.getItem();
	}

	public void setFinalAmount(int amount) {
		finalamount = amount;	
	}
	
	public int getFinalAmount() {
		return finalamount;
	}

	public e_type getType() {
		return type;
	}

	public void setFinalPrice(double price) {
		finalprice = price;
		
	}

	public double getFinalPrice() {
		return finalprice;		
	}

	public double getPrice() {
		return price / Config.STACK_SIZE_ON_SIGN;
	}

	public int getItemId() {
		return s.getItemId();
	}

	public String getItemName() {
		return s.getItemName();
	}

	public Location getLocation() {
		return loc;
	}
	
	
	

}
