package eu.sidzej.wc.utils;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.economy.EconomyResponse.ResponseType;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import eu.sidzej.wc.WoodCurrency;
import eu.sidzej.wc.config.Lang;

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
		return eco.getBalance(p);
	}

	public static synchronized boolean withdraw(Player p, double amount) {
	    if (!eco.hasAccount(p)){
	        p.sendMessage(Lang.BLOCKED_ACC);
	        return false;
	    }
		if (eco.getBalance(p) >= amount) {
			tmp = eco.withdrawPlayer(p, amount);
			if (tmp.transactionSuccess())
				return true;
			else {
				p.sendMessage(Lang.GENERAL_PROBLEM);
				if (tmp.type == ResponseType.FAILURE)
					Log.error(tmp.errorMessage);
				return false;
			}

		}
		p.sendMessage(Lang.NOT_ENOUGH_MONEY);
		return false;
	}

	public static boolean deposit(Player p, double amount) {
	    if (!eco.hasAccount(p)){
            p.sendMessage(Lang.BLOCKED_ACC);
            return false;
        }
		tmp = eco.depositPlayer(p, amount);
		if (!tmp.transactionSuccess()) {
			p.sendMessage(Lang.GENERAL_PROBLEM);
			if (tmp.type == ResponseType.FAILURE)
				Log.error(tmp.errorMessage);
			return false;
		}
		return true;
	}

}
