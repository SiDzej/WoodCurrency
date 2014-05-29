package eu.sidzej.wc;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.google.common.base.Joiner;

import eu.sidzej.wc.commands.*;
import eu.sidzej.wc.config.Lang;
import eu.sidzej.wc.utils.Log;

public class CommandHandler implements CommandExecutor{
	
	public HashMap<String, CommandInterface> commands = new HashMap<String, CommandInterface>();
    public ArrayList<String> sortedCommands = new ArrayList<String>();
    @SuppressWarnings("unused")
	private final WoodCurrency plugin;

    /**
     *
     * @param plugin
     */
    public CommandHandler(WoodCurrency plugin) {

        this.plugin = plugin;

        commands.put("help", new Help(plugin));
        commands.put("ban", new Ban(plugin));
        commands.put("unban", new Unban(plugin));
        commands.put("info", new Info(plugin));
        commands.put("resetday", new ResetDayLimit(plugin));
        commands.put("resettier", new ResetTier(plugin));
        commands.put("top", new Top(plugin));        

        for (String s : commands.keySet()) {
            sortedCommands.add(s);
        }
        Collections.sort(sortedCommands, Collator.getInstance());
        Log.debug("Commands enabled: " + Joiner.on(", ").join(sortedCommands));
    }

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length >= 1) {
            String subCmd = args[0].toLowerCase();
            if (commands.containsKey(subCmd)) {
            	if (subCmd.equalsIgnoreCase("pass"))
            		subCmd = "password";
                if (!sender.hasPermission("ma." + subCmd)) {
                    sender.sendMessage(Lang.NO_PERMISSION);
                    return true;
                }
                commands.get(subCmd).dispatch(sender, args);
                return true;
            }
        }
        commands.get("help").dispatch(sender, args);
        return true;
	}
}
