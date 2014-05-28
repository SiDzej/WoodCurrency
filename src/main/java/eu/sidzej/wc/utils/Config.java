package eu.sidzej.wc.utils;

import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;

import eu.sidzej.wc.WoodCurrency;


public class Config {

	public static final String SIGN_FIRST_LINE_INPUT = "wc";
	public static final String SIGN_FIRST_LINE = ChatColor.COLOR_CHAR+"4WoodCurrency";
	public static final int STACK_SIZE = 64;
	public static final int MAX_SELL = 1024;
	public static String host, port, database, pass, user; // db
	public static boolean debugEnabled, opPerm;
	public static String SIGN_TEXT;
	public static String[] labels;

	private static Configuration config;
	private WoodCurrency plugin;

	public Config(WoodCurrency plugin) {
		this.plugin = plugin;
		
		config = plugin.getConfig().getRoot();
		config.options().copyDefaults(true);
		//config.set("version", plugin.version);
		plugin.saveConfig();

		//Sign set
		SIGN_TEXT = ChatColor.COLOR_CHAR+"4" + this.plugin.getName();
		labels = new String[]{"[WoodCurrency]", "[xWCx]", "wc", SIGN_TEXT};
		
		//
		opPerm = config.getBoolean("op-permissions"); //ops have all permissions
		debugEnabled = config.getBoolean("debug");
		/** DB **/
		host = config.getString("mysql.host");
		port = config.getString("mysql.port");
		database = config.getString("mysql.database");
		pass = config.getString("mysql.password");
		user = config.getString("mysql.user");
	}
}
