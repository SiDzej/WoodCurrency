package eu.sidzej.wc.sign;

import java.util.regex.Pattern;

import org.bukkit.block.Block;
import org.bukkit.block.Sign;

public class SignValidator {
    public static final byte NAME_LINE = 0;
    public static final byte TYPE_LINE = 1;
    public static final byte PRICE_LINE = 2;
    public static final byte ITEM_LINE = 3;

    public static final Pattern[] SHOP_SIGN_PATTERN = {
            Pattern.compile("^?[\\w -.&\\d]*$"),
            Pattern.compile("^\\s*(Buy|Sell|b|s)\\s*(:\\s*(Buy|Sell|b|s)\\s*)?$"),
            Pattern.compile("(?i)^\\s*[\\d.]+\\s*(:\\s*[\\d.]+\\s*)?$"),
            Pattern.compile("(?i)^[\\d\\w? #:-]+ wood$") //Pattern.compile("^[\\w? #:-]+$")
    };

    public static boolean isValid(Sign sign) {
        return isValid(sign.getLines());
    }

    public static boolean isValid(String[] line) {
        return isValidPreparedSign(line) && (line[TYPE_LINE].toUpperCase().contains("B") || line[TYPE_LINE].toUpperCase().contains("S")) && !line[NAME_LINE].isEmpty();
    }

    public static boolean isValid(Block sign) {
        return (sign.getState() instanceof Sign) && isValid((Sign) sign.getState());
    }

    /*public static boolean canAccess(Player player, Sign sign) {
        if (player == null) return false;
        if (sign == null) return true;

        return NameManager.canUseName(player, sign.getLine(NAME_LINE));
    }*/

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