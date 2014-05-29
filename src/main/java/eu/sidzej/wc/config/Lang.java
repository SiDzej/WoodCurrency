package eu.sidzej.wc.config;

import org.bukkit.configuration.Configuration;

import eu.sidzej.wc.WoodCurrency;
import eu.sidzej.wc.utils.Log;

public class Lang {
	public static String NO_PERMISSION,GENERAL_PROBLEM;
	
	private static ConfigAccessor accessor;
	private static Configuration locale;
	
	public Lang(WoodCurrency plugin){
		accessor = new ConfigAccessor(plugin, "locale.yml");
		locale = accessor.getConfig().getRoot();
		locale.options().copyDefaults(true);
		accessor.saveConfig();

		
		NO_PERMISSION = locale.getString("NO_PERMISSION");
		GENERAL_PROBLEM = locale.getString("GENERAL_PROBLEM");
		//MAX_SELL_PER_DAY = config.getInt("limits.MAX_SELL_PER_DAY");
		
		Log.info(NO_PERMISSION);
		

	}

}
