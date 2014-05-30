package eu.sidzej.wc.commands;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import eu.sidzej.wc.PlayerManager;
import eu.sidzej.wc.PlayerManager.PlayerData;
import eu.sidzej.wc.WoodCurrency;
import eu.sidzej.wc.config.Lang;
import eu.sidzej.wc.db.DBUtils;
import eu.sidzej.wc.utils.PlayerUtils;

public class ResetTier implements CommandInterface {
	@SuppressWarnings("unused")
	private final WoodCurrency plugin;
    private final String usage 	= "<nick>";
    private final String desc 	= Lang.CMD_RESET_TIER;
    private final String name	= "resettier";
	
	public ResetTier(WoodCurrency plugin){
		this.plugin = plugin;
	}
	
	@Override
    public void dispatch(CommandSender sender, String[] args) {
		PlayerData data;
		String playerName = "";
		if (args.length > 1) {
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
			data.resetTier();
			DBUtils.UpdatePlayer(data);
			sender.sendMessage(Lang.A_PLAYER_TIER_RESET + " " + playerName);
			return;
		} 
		Help.getHelp(sender, args[0]);
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
