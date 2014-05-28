package eu.sidzej.wc;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.inventory.ItemStack;

import eu.sidzej.wc.sign.SignValidator;

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
	
	public WCSign(Location l) {
		Block b = Bukkit.getServer().getWorld(l.getWorld().getName()).getBlockAt(l);
		Sign s = (Sign)b.getState();
		item = SignValidator.getItemStack(s);
		type = SignValidator.getShopType(s);
		sellprice = SignValidator.getPrice(s, e_type.SELL);
		buyprice = SignValidator.getPrice(s, e_type.BUY);		
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
