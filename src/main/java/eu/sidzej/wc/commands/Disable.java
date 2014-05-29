package eu.sidzej.wc.commands;

import org.bukkit.command.CommandSender;

import eu.sidzej.wc.WoodCurrency;
import eu.sidzej.wc.config.Lang;

public class Disable implements CommandInterface {
	private final WoodCurrency plugin;
    private final String usage 	= "";
    private final String desc 	= Lang.CMD_DISABLE;
    private final String name	= "disable";
	
	public Disable(WoodCurrency plugin){
		this.plugin = plugin;
	}
	
	@Override
    public void dispatch(CommandSender sender, String[] args) {
        if(sender.hasPermission("woodcurrency.disable"))
        	plugin.disable("Disabled on players command.");
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
