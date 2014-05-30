package eu.sidzej.wc.commands;

import org.bukkit.command.CommandSender;

import eu.sidzej.wc.WoodCurrency;
import eu.sidzej.wc.config.Config;
import eu.sidzej.wc.config.Lang;

public class Example implements CommandInterface{
	@SuppressWarnings("unused")
	private final WoodCurrency plugin;
    private final String usage 	= "";
    private final String desc 	= Lang.CMD_EXAMPLE;
    private final String name	= "example";
	
	public Example(WoodCurrency plugin){
		this.plugin = plugin;
	}
	
	@Override
    public void dispatch(CommandSender sender, String[] args) {
        sender.sendMessage(Config.SIGN_FIRST_LINE_INPUT);
        sender.sendMessage("b:s");
        sender.sendMessage("buy price : sell price");
        sender.sendMessage("(Oak|Acacia|Jungle|Birch|DarkOak|Spruce) wood");
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
