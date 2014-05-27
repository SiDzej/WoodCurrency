package eu.sidzej.wc.utils;

import net.milkbowl.vault.permission.Permission;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;

import eu.sidzej.wc.WoodCurrency;

public class Permissions {

	private WoodCurrency plugin;
	private static PermissionPlugins handler = PermissionPlugins.BUKKITPERMS;
	private static net.milkbowl.vault.permission.Permission vaultPermissions;

	private enum PermissionPlugins {
		VAULT, BUKKITPERMS
	}

	public Permissions(WoodCurrency plugin) {
		this.plugin = plugin;

		PluginManager pm = plugin.getServer().getPluginManager();

		if (pm.isPluginEnabled("Vault")) {
			RegisteredServiceProvider<Permission> rsp = this.plugin.getServer()
					.getServicesManager().getRegistration(Permission.class);
			if (rsp != null) {
				vaultPermissions = rsp.getProvider();
				if (vaultPermissions != null) {
					handler = PermissionPlugins.VAULT;
					Log.info("Using " + vaultPermissions.getName() + " for user permissions");
				}
			}
		} else
			Log.info("No permission handler detected, defaulting to superperms.");
	}

	private static boolean hasPermission(CommandSender sender, String node) {
		if (!(sender instanceof Player))
			return true;
		Player player = (Player) sender;
		if (Config.opPerm && player.isOp())
			return true;
		switch (handler) {
			case VAULT:
				return vaultPermissions.has(player, node);
			case BUKKITPERMS:
				return player.hasPermission(node);
		}
		return false;
	}

	public static boolean placeSign(CommandSender p) {
		return hasPermission(p, "woodcurency.placeSign");
	}

	public static boolean removeSign(CommandSender p) {
		return hasPermission(p, "woodcurency.removeSign");
	}

	public static boolean buy(CommandSender p) {
		return hasPermission(p, "woodcurency.buy");
	}

	public static boolean sell(CommandSender p) {
		return hasPermission(p, "woodcurency.sell");
	}
}
