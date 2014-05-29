package eu.sidzej.wc.commands;

import org.bukkit.command.CommandSender;

import eu.sidzej.wc.config.Lang;

public interface CommandInterface {	
	final String bad = Lang.CMD_BAD_USAGE;
	void dispatch(CommandSender sender, String[] args);
    String name();
    String desc();
    String usage();  
}
