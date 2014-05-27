package eu.sidzej.wc.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import eu.sidzej.wc.WCSign.e_type;
import eu.sidzej.wc.sign.SignValidator;
import eu.sidzej.wc.utils.BlockUtils;

public class SignCreationEvent extends Event{
	private static final HandlerList handlers = new HandlerList();
	private Player p;
	private String[] lines;
	private boolean two_prices = false;
	private e_states state = e_states.OK;
	private e_type type = e_type.NONE;
	private double sellPrice = -1.0,buyPrice = -1.0;

	public SignCreationEvent(Player player, String[] lines) {
		p = player;
		this.lines = lines;
	}

	public boolean isCancelled() {
		return state != e_states.OK;
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
	
	public static enum e_states{
		OK,
		BAD_NAME_LINE,
		BAD_TYPE_LINE,
		BAD_PRICE_LINE,
		BAD_ITEM_LINE,
		BAD_TYPE_TO_PRICE_LINES,
		NO_PERMISSIONS,
	}

	public e_states getState() {
		return state;
	}
	
	public void setState(e_states s){
		state = s;
	}

	public void setType(e_type t) {
		type = t;
	}
	public e_type getType() {
		return type;
	}

	public void setBuyPrice(double p) {
		buyPrice = p;		
	}

	public void setSellPrice(double p) {
		sellPrice = p;
	}
	
	public double getSellPrice(){
		return sellPrice;
	}
	
	public double getBuyPrice(){
		return buyPrice;
	}
	
	public ItemStack getItem(){
		return BlockUtils.getItemStack(this.lines[SignValidator.ITEM_LINE]);
	}
}
