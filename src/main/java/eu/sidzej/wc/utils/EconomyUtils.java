package eu.sidzej.wc.utils;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.economy.EconomyResponse.ResponseType;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import eu.sidzej.wc.WoodCurrency;

public final class EconomyUtils {
	private WoodCurrency plugin;
	private static Economy eco = null;
	private static EconomyResponse tmp;

	public EconomyUtils(WoodCurrency plugin) {
		this.plugin = plugin;
		RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economyProvider == null) {
			this.plugin.disable("Can't get Vault economy hook.");
			return;
		}
		eco = economyProvider.getProvider();
		if (eco == null) {
			this.plugin.disable("Can't get Vault economy hook.()");
		}
	}

	public static String format(double amount){
		return eco.format(amount);
	}
	
	public static double getBalance(Player p) {
		return eco.getBalance(p.getName());
	}

	public static synchronized boolean withdraw(Player p, double amount) {
		if (eco.getBalance(p.getName()) >= amount) {
			tmp = eco.withdrawPlayer(p.getName(), amount);
			if (tmp.transactionSuccess())
				return true;
			else {
				p.sendMessage("Something goes wrong. Transaction failed!");
				if (tmp.type == ResponseType.FAILURE)
					Log.error(tmp.errorMessage);
				return false;
			}

		}
		p.sendMessage("You lack the money to buy this!");
		return false;
	}

	public static boolean deposit(Player p, double amount) {
		tmp = eco.depositPlayer(p.getName(), amount);
		if (!tmp.transactionSuccess()) {
			p.sendMessage("Something goes wrong. Transaction failed!");
			if (tmp.type == ResponseType.FAILURE)
				Log.error(tmp.errorMessage);
			return false;
		}
		return true;
	}

}
