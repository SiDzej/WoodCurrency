package eu.sidzej.wc.sign;

import java.util.regex.Pattern;

import org.bukkit.block.Sign;
import org.bukkit.inventory.ItemStack;

import eu.sidzej.wc.WCSign.e_type;
import eu.sidzej.wc.utils.BlockUtils;

public class SignValidator {
    public static final byte NAME_LINE = 0;
    public static final byte TYPE_LINE = 1;
    public static final byte PRICE_LINE = 2;
    public static final byte ITEM_LINE = 3;

    public static final Pattern[] SHOP_SIGN_PATTERN = {
            Pattern.compile("^?[\\w -.&\\d]*$"),
            Pattern.compile("(?i)^\\s*(Buy|Sell|b|s)\\s*(:\\s*(Buy|Sell|b|s)\\s*)?$"),
            Pattern.compile("(?i)^\\s*[\\d.]+\\s*(:\\s*[\\d.]+\\s*)?$"),
            Pattern.compile("(?i)^[\\d\\w? #:-]+ wood$") //Pattern.compile("^[\\w? #:-]+$")
    };

    public static e_type getShopType(Sign s){
    	if(!isValidPreparedSign(s.getLines()))
    		return e_type.NONE;
    	String line = s.getLine(TYPE_LINE);
    	if((line.split(":")).length >1)
    		return e_type.BUYSELL;
    	else if(line.equalsIgnoreCase("Buy"))
    		return e_type.BUY;
    	else
    		return e_type.SELL;
    }
    
    public static double getPrice(Sign s, e_type type){
    	if(!type.equals(e_type.NONE)){
    		if(type.equals(e_type.BUY))
    			return Double.parseDouble(s.getLine(PRICE_LINE).split(" : ")[0]);
    		else
    			return Double.parseDouble(s.getLine(PRICE_LINE).split(" : ")[1]);
    	}
    	else
    		return Double.parseDouble(s.getLine(PRICE_LINE));
    }
    
    public static ItemStack getItemStack(Sign s){
    	return BlockUtils.getItemStack(s.getLine(SignValidator.ITEM_LINE));
    }

    public static boolean isValidPreparedSign(String[] lines) {
        for (int i = 0; i < 4; i++) {
            if (!SHOP_SIGN_PATTERN[i].matcher(lines[i]).matches()) {
                return false;
            }
        }
        return (lines[TYPE_LINE].indexOf(":") == lines[TYPE_LINE].lastIndexOf(":") &&
        		lines[PRICE_LINE].indexOf(":") == lines[PRICE_LINE].lastIndexOf(":") );
    }
}