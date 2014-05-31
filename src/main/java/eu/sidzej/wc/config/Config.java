package eu.sidzej.wc.config;

import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;

import eu.sidzej.wc.WoodCurrency;

public class Config {

	public static String SIGN_FIRST_LINE_INPUT;
	public static String SIGN_FIRST_LINE;
	public static int STACK_SIZE_ON_SIGN;
	public static String CUSTOM_HELP;
	public static int MAX_SELL_PER_DAY;
	public static String host, port, database, pass, user; // db
	public static boolean debugEnabled, opPerm;
	public static String SIGN_TEXT;
	public static String[] labels;

	private static Configuration config;
	public static String lang;

	// private WoodCurrency plugin;

	public Config(WoodCurrency plugin) {
		// this.plugin = plugin;

		config = plugin.getConfig().getRoot();
		config.options().copyDefaults(true);
		// config.set("version", plugin.version);
		plugin.saveConfig();
		
		// Sign set
		SIGN_FIRST_LINE = config.getString("SIGN_FIRST_LINE");
		if (SIGN_FIRST_LINE != null)
			SIGN_FIRST_LINE = ChatColor.translateAlternateColorCodes('&', SIGN_FIRST_LINE);
		SIGN_FIRST_LINE_INPUT = config.getString("SIGN_FIRST_LINE_INPUT");
		STACK_SIZE_ON_SIGN = config.getInt("STACK_SIZE_ON_SIGN");
		// limits
		MAX_SELL_PER_DAY = config.getInt("limits.MAX_SELL_PER_DAY");
		CUSTOM_HELP = config.getString("CUSTOM_HELP");
		//
		lang = config.getString("language");
		opPerm = config.getBoolean("op-permissions"); // ops have all permissions
		debugEnabled = config.getBoolean("debug");
		/** DB **/
		host = config.getString("mysql.host");
		port = config.getString("mysql.port");
		database = config.getString("mysql.database");
		pass = config.getString("mysql.password");
		user = config.getString("mysql.user");
	}
}
