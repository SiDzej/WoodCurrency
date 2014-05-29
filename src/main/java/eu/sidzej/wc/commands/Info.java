package eu.sidzej.wc.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import eu.sidzej.wc.PlayerManager;
import eu.sidzej.wc.WoodCurrency;
import eu.sidzej.wc.PlayerManager.PlayerData;
import eu.sidzej.wc.config.Lang;
import eu.sidzej.wc.db.DBUtils;

public class Info implements CommandInterface {
	@SuppressWarnings("unused")
	private final WoodCurrency plugin;
    private final String usage 	= "";
    private final String desc 	= Lang.CMD_INFO;
    private final String name	= "info";
	
	public Info(WoodCurrency plugin){
		this.plugin = plugin;
	}
	
	@Override
    public void dispatch(CommandSender sender, String[] args) {
		if (sender.hasPermission("woodcurrency.info")) {
			if (args.length > 1) {
				PlayerData data = PlayerManager.getPlayerData(args[1]);
				if (data != null) {
					data.setBlocked(false);
					DBUtils.UpdatePlayer(data);
					sender.sendMessage(args[1] + " " + Lang.A_UNBANNED);
					return;
				} else {
					@SuppressWarnings("deprecation")
					OfflinePlayer p = Bukkit.getOfflinePlayer(args[1]);// TODO do'h wtf
					if (p != null) {
						if (DBUtils.UpdatePlayerBan(p.getUniqueId(), false))
							sender.sendMessage(args[1] + " " + Lang.A_UNBANNED);
						return;
					}

				}
				sender.sendMessage(Lang.A_PLAYER_NOT_FOUND + " " + args[1]);
			} else
				Help.getHelp(sender, args[0]);
		}
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
