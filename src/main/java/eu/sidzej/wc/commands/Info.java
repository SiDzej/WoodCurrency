package eu.sidzej.wc.commands;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import eu.sidzej.wc.PlayerManager;
import eu.sidzej.wc.PlayerManager.PlayerData;
import eu.sidzej.wc.WoodCurrency;
import eu.sidzej.wc.config.Config;
import eu.sidzej.wc.config.Lang;
import eu.sidzej.wc.db.DBUtils;
import eu.sidzej.wc.utils.PlayerUtils;

public class Info implements CommandInterface {
	@SuppressWarnings("unused")
	private final WoodCurrency plugin;
	private final String usage = "[nick]";
	private final String desc = Lang.CMD_INFO;
	private final String name = "info";

	public Info(WoodCurrency plugin) {
		this.plugin = plugin;
	}

	@Override
	public void dispatch(CommandSender sender, String[] args) {
		PlayerData data;
		String playerName = "";
		if (args.length > 1) {
			if (!sender.getName().equals(args[1]))
				if (sender instanceof Player) {
					if (!sender.hasPermission("woodcurrency.info.others"))
						if (sender.isOp() && !Config.opPerm) {
							sender.sendMessage(Lang.NO_PERMISSION);
							return;
						}
				}

			playerName = args[1];
			data = PlayerManager.getPlayerData(playerName);
			if (data == null) {
				OfflinePlayer p = PlayerUtils.getOfflinePlayer(playerName);
				data = DBUtils.getPlayerData(p.getUniqueId());
			}
			if (data == null) {
				sender.sendMessage(Lang.A_PLAYER_NOT_FOUND + " " + playerName);
				return;
			}
		} else if (!(sender instanceof Player)) {
			sender.sendMessage(Lang.NOT_AVAILABLE_FOR_CONSOLE);
			Help.getHelp(sender, args[0]);
			return;
		} else {
			playerName = sender.getName();
			data = PlayerManager.getPlayerData(((Player) sender).getUniqueId());
		}
		final String l,b,s;
		if(Lang.A_INFO != null){
			final String[] info = Lang.A_INFO.split(":");
			if(info.length == 3){
				l=info[0].trim();b=info[1].trim();s=info[2].trim();
			}
			else
				l=b=s=null;				
		}else
			l=b=s=null;	
		
		PlayerUtils.checkDailyLimits(data,false);
		sender.sendMessage(ChatColor.AQUA + playerName + " status:");
		sender.sendMessage(String.format("%s - " + ChatColor.RESET + "%s/%s", l,
				data.getDay(), data.getDayLimit()));
		sender.sendMessage(String.format("%s - " + ChatColor.RESET + "%s", b,
				data.getTotalBuy()));
		sender.sendMessage(String.format("%s - " + ChatColor.RESET + "%s", s,
				data.getTotalSell()));
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public String desc() {
		return desc;
	}

	@Override
	public String usage() {
		return usage;
	}

}
