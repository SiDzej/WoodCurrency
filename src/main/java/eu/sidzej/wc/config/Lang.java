package eu.sidzej.wc.config;

import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;

import eu.sidzej.wc.WoodCurrency;

public class Lang {

	public static String NO_PERMISSION, GENERAL_PROBLEM, NO_SPACE_IN_INVENTORY, NOT_ENOUGH_MONEY,
			NO_REQUIRED_ITEM, DAY_LIMIT_REACHED, BOUGHT, SOLD, SHOP_CREATED,
			WRONG_NUMBER_OF_TYPES_TO_PRICES, BAD_FIRST_LINE, BAD_SECOND_LINE, BAD_THIRD_LINE,
			BAD_FOURTH_LINE, CANT_CREATE_SHOP, SHOP_DESTROYED;

	private static ConfigAccessor accessor;
	private static Configuration locale;

	public Lang(WoodCurrency plugin) {
		accessor = new ConfigAccessor(plugin, "locale.yml");
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
