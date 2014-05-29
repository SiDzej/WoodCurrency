package eu.sidzej.wc.config;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;

import eu.sidzej.wc.WoodCurrency;
import eu.sidzej.wc.utils.Log;

public class Lang {

	public static String CMD_BAN, CMD_BAD_USAGE, CMD_UNBAN, CMD_INFO, CMD_TOP, CMD_RESET_TIER,
			CMD_RESET_DAY, CMD_HELP, CMD_UNKNOWN;

	public static String NO_PERMISSION, GENERAL_PROBLEM, NO_SPACE_IN_INVENTORY, NOT_ENOUGH_MONEY,
			NO_REQUIRED_ITEM, DAY_LIMIT_REACHED, BOUGHT, SOLD, SHOP_CREATED,
			WRONG_NUMBER_OF_TYPES_TO_PRICES, BAD_FIRST_LINE, BAD_SECOND_LINE, BAD_THIRD_LINE,
			BAD_FOURTH_LINE, CANT_CREATE_SHOP, SHOP_DESTROYED;

	private static ConfigAccessor accessor;
	private static Configuration locale;

	public Lang(WoodCurrency plugin) {
		File folder = new File(plugin.getDataFolder() + "/lang");
		if (!folder.exists()) {
			try {
				folder.mkdir();
			} catch (Exception e) {
				Log.error(e.getMessage());
			}
		}
		String lang = (Config.lang.isEmpty() || Config.lang == null) ? "en.yml" : Config.lang;
		accessor = new ConfigAccessor(plugin, "lang/" + lang + ".yml");
		locale = accessor.getConfig().getRoot();
		locale.options().copyDefaults(true);
		accessor.saveConfig();

		NO_PERMISSION = getString("NO_PERMISSION");

		NO_SPACE_IN_INVENTORY = getString("NO_SPACE_IN_INVENTORY");
		NOT_ENOUGH_MONEY = getString("NOT_ENOUGH_MONEY");
		NO_REQUIRED_ITEM = getString("NO_REQUIRED_ITEM");
		DAY_LIMIT_REACHED = getString("DAY_LIMIT_REACHED");

		BOUGHT = getString("BOUGHT");
		SOLD = getString("SOLD");

		BAD_FIRST_LINE = getString("BAD_FIRST_LINE");
		BAD_SECOND_LINE = getString("BAD_SECOND_LINE");
		BAD_THIRD_LINE = getString("BAD_THIRD_LINE");
		BAD_FOURTH_LINE = getString("BAD_FOURTH_LINE");
		CANT_CREATE_SHOP = getString("NO_PERMISSION");
		WRONG_NUMBER_OF_TYPES_TO_PRICES = getString("WRONG_NUMBER_OF_TYPES_TO_PRICES");

		SHOP_CREATED = getString("SHOP_CREATED");
		SHOP_DESTROYED = getString("SHOP_DESTROYED");

		GENERAL_PROBLEM = getString("GENERAL_PROBLEM");
		// MAX_SELL_PER_DAY = config.getInt("limits.MAX_SELL_PER_DAY");

	}

	private String getString(String s) {
		if (s == null)
			return null;
		return ChatColor.translateAlternateColorCodes('&', locale.getString(s));
	}

}
