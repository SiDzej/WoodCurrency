package eu.sidzej.wc.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import eu.sidzej.wc.PlayerManager;
import eu.sidzej.wc.WoodCurrency;
import eu.sidzej.wc.config.Lang;
import eu.sidzej.wc.db.DBUtils;

public class Top implements CommandInterface {
	@SuppressWarnings("unused")
	private final WoodCurrency plugin;
    private final String usage 	= "";
    private final String desc 	= Lang.CMD_TOP;
    private final String name	= "top";
	
	public Top(WoodCurrency plugin){
		this.plugin = plugin;
	}
	
	@Override
    public void dispatch(CommandSender sender, String[] args) {
		final String topline = "=======[ "+ Lang.A_TOP_LIST + ChatColor.GRAY + " ]=======";
        sender.sendMessage(ChatColor.GRAY + topline);
        PlayerManager.saveAll();
        for(String s: DBUtils.getTopTen())
        	sender.sendMessage(s);
        
        
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
