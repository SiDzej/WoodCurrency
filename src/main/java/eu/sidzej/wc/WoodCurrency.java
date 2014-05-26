package eu.sidzej.wc;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import eu.sidzej.wc.listeners.*;
import eu.sidzej.wc.listeners.sign.*;

public class WoodCurrency extends JavaPlugin{
	private static WoodCurrency plugin;
	
	//private File langFile; //TODO
	public static String name;

	public void onEnable() {
		plugin = this;
		
		Logger.getLogger("Minecraft").log(Level.SEVERE, String.format("%s %s", "blaba", "jedeeem"));
		// PluginManager pm = getServer().getPluginManager();

		name = this.getDescription().getName();
		
		registerListener(new PlayerListener());
		registerListener(new SignChangeListener());
		
		
		registerListener(new ShopCreatedMessage());
		registerListener(new ShopCreatedRegister());
		registerListener(new SignCreationMonitor());
		registerListener(new SignItemLineListener());
		registerListener(new SignNameLineListener());
		registerListener(new SignPriceLineListener());
		registerListener(new SignTypeLineListener());
	}

	public void onDisable() { 
		
	}

	public void disable() {
		Bukkit.getPluginManager().disablePlugin(this);
	}

	public void disable(String msg) {
		
		Bukkit.getPluginManager().disablePlugin(this);
	}
	
	
	public void registerListener(Listener l){
		getServer().getPluginManager().registerEvents(l,this);
	}
	
	public static void callEvent(Event event) {
        Bukkit.getPluginManager().callEvent(event);
    }
}