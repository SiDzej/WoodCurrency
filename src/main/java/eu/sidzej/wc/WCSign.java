package eu.sidzej.wc;

import org.bukkit.inventory.ItemStack;

public class WCSign {
	private e_type type = e_type.NONE;
	private double sellprice = -1.0;
	private double buyprice = -1.0;
	private ItemStack item;
	
	public WCSign(ItemStack item, double sell, double buy, e_type type){
		this.item = item;
		sellprice = sell;
		buyprice = buy;
		this.type = type;		
	}
	
	public WCSign() {
		// TODO Auto-generated constructor stub
	}

	public double getSellPrice(){
		return sellprice;
	}
	
	public double getBuyPrice(){
		return buyprice;
	}
	
	public ItemStack getItem(){
		return item.clone();
	}
	
	public e_type getType(){
		return type;
	}
	
	public static enum e_type{
		NONE,
		BUY,
		SELL,
		BUYSELL,
	}

}
