package eu.sidzej.wc.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import eu.sidzej.wc.WoodCurrency;
import eu.sidzej.wc.config.Lang;

public class Help implements CommandInterface {
	private final WoodCurrency plugin;
    private final String usage 	= "[command]";
    private final String desc 	= Lang.CMD_HELP;
    private final String name	= "help";
	
	public Help(WoodCurrency plugin){
		this.plugin = plugin;
	}
	
	@Override
    public void dispatch(CommandSender sender, String[] args) {
        if (args.length >= 2) {
            String s = args[1];
            if (plugin.commandHandler.commands.containsKey(s)) {
                sender.sendMessage(helpStringBuilder(
                        plugin.commandHandler.commands.get(s).name(),
                        plugin.commandHandler.commands.get(s).desc(),
                        plugin.commandHandler.commands.get(s).usage()));
                return;
            } else {
                sender.sendMessage(ChatColor.RED + "Invalid sub command: " 
                        + ChatColor.WHITE + s);
                return;
            }
        }
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
        		"&b-----[  &f"+ plugin.getName() +"&b - &f" + plugin.version + "&b ]-----"));
        for (String s : plugin.commandHandler.sortedCommands) {
        	if (s.equals("pass")){
        		sender.sendMessage(helpStringBuilder("pass","Alternative for /irc password.","[password]"));
        				continue;
        	}
            if (plugin.commandHandler.commands.containsKey(s)) {
                sender.sendMessage(helpStringBuilder(
                        plugin.commandHandler.commands.get(s).name(),
                        plugin.commandHandler.commands.get(s).desc(),
                        plugin.commandHandler.commands.get(s).usage()));
            }
        }

    }
	
	private String helpStringBuilder(String n, String d, String u) {
        return ChatColor.translateAlternateColorCodes('&', "&b/irc "
                + n + " &e" + u + " &f- " + d);
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
