package eu.sidzej.wc;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import eu.sidzej.wc.db.DBUtils;
import eu.sidzej.wc.db.Database;
import eu.sidzej.wc.listeners.*;
import eu.sidzej.wc.listeners.sign.*;
import eu.sidzej.wc.listeners.transaction.*;
import eu.sidzej.wc.utils.Config;
import eu.sidzej.wc.utils.EconomyUtils;
import eu.sidzej.wc.utils.Log;

public class WoodCurrency extends JavaPlugin {
	@SuppressWarnings("unused")
	private static WoodCurrency plugin;

	public static PlayerManager playerManager = PlayerManager.getInstance();
	public static ProtectionManager protectionManager = ProtectionManager.getInstance();
	
	public Database db;
	public EconomyUtils economy;

	// private File langFile; //TODO
	public static String name;
	public static String version;
	public Config config;

	public void onEnable() {
		plugin = this;

		name = this.getDescription().getName();
		version = this.getDescription().getVersion();
		
		config = new Config(this);
		Log.debug("Debug enabled!"); // log only when enabled in config :)


		// Required - Vault,
		checkDependencies(getServer().getPluginManager());
		//DB connections
		try {
			db = new Database(this);
			if (!db.valid) {
				this.disable("Database error");
				return;
			}
		} catch (ClassNotFoundException e) {
			Log.error("Can't join database. com.mysql.jdbc.Driver not found.");
			this.disable();
		}

		
		if(!DBUtils.loadShops())
			this.disable("Shop loading error");
		if(!DBUtils.loadOnlinePlayers())
			this.disable("Players loading error");
		
		registerListeners();
		/*
		commandHandler = new CommandHandler(this);
		getCommand("wc").setExecutor(commandHandler);
		*/
		economy = new EconomyUtils(this);
	}

	public void onDisable() {

	}

	public void disable() {
		Bukkit.getPluginManager().disablePlugin(this);
	}

	public void disable(String msg) {
		Log.error(msg);
		Bukkit.getPluginManager().disablePlugin(this);
	}
	
	private void checkDependencies(PluginManager pm) {
		if (!pm.isPluginEnabled("Vault")) {
			Log.error("Vault is required for this plugin.");
			this.disable("Vault is required!");
		}

	}

	private void registerListeners() {
		// main
		registerListener(new PlayerListener());
		registerListener(new SignChangeListener());
		registerListener(new BlockBreakListener());

		// shop creation related
		registerListener(new ShopCreatedMessage());
		registerListener(new ShopCreatedRegister());
		registerListener(new SignCreationMonitor());
		registerListener(new SignItemLineListener());
		registerListener(new SignNameLineListener());
		registerListener(new SignPriceLineListener());
		registerListener(new SignTypeLineListener());
		
		// shop transaction related
		registerListener(new TransactionPrepareAction());
		registerListener(new TransactionPrepareEconomy());
		registerListener(new TransactionPrepareInventory());
		registerListener(new TransactionPrepareMonitor());
		registerListener(new TransactionPreparePerms());
		
		registerListener(new TransactionEconomy());
		registerListener(new TransactionInventory());
		registerListener(new TransactionMessage());
		registerListener(new TransactionMonitor());
		
	}

	public void registerListener(Listener l) {
		getServer().getPluginManager().registerEvents(l, this);
	}

	public static void callEvent(Event event) {
		Bukkit.getPluginManager().callEvent(event);
	}
}