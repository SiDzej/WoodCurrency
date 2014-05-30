package eu.sidzej.wc;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.google.common.base.Joiner;

import eu.sidzej.wc.commands.*;
import eu.sidzej.wc.config.Config;
import eu.sidzej.wc.config.Lang;
import eu.sidzej.wc.utils.Log;

public class CommandHandler implements CommandExecutor, TabCompleter {

	private static CommandHandler instance;
	private HashMap<String, CommandInterface> commands = new HashMap<String, CommandInterface>();
	private ArrayList<String> sortedCommands = new ArrayList<String>();
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
		commands.put("disable", new Disable(plugin));
		commands.put("example", new Example(plugin));

		for (String s : commands.keySet()) {
			sortedCommands.add(s);
		}
		Collections.sort(sortedCommands, Collator.getInstance());
		Log.debug("Commands enabled: " + Joiner.on(", ").join(sortedCommands));
	}

	public static CommandHandler getInstance(WoodCurrency plugin) {
		if (instance == null)
			instance = new CommandHandler(plugin);
		return instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length >= 1) {
			String subCmd = args[0].toLowerCase();
			if (commands.containsKey(subCmd)) {
				if (sender instanceof Player) {
					if (!sender.hasPermission("woodcurrency." + subCmd))
						if (sender.isOp() && !Config.opPerm) {
							sender.sendMessage(Lang.NO_PERMISSION);
							return true;
						}
				}

				commands.get(subCmd).dispatch(sender, args);
				return true;
			}
		}
		commands.get("help").dispatch(sender, args);
		return true;
	}

	public static boolean containsCmd(String s) {
		return instance.commands.containsKey(s);
	}

	public static CommandInterface get(String s) {
		return instance.commands.get(s);
	}

	public static List<String> getSortedCmdList() {
		return instance.sortedCommands;
	}

	@Override
	public List<String> onTabComplete(CommandSender p, Command cmd, String alias, String[] partial) {
		String par = partial[partial.length - 1];
		List<String> out = new ArrayList<String>();
		for (String s : commands.keySet())
			if (s.startsWith(par))
				if ((p instanceof Player && p.hasPermission("woodcurrency." + s))
						|| (p.isOp() && Config.opPerm) || !(p instanceof Player)) {
					out.add(s);
				}

		return out;
	}
}
