package eu.sidzej.wc.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import eu.sidzej.wc.CommandHandler;
import eu.sidzej.wc.WoodCurrency;
import eu.sidzej.wc.config.Lang;

public class Help implements CommandInterface {
	private final WoodCurrency plugin;
	private final String usage = "[command]";
	private final String desc = Lang.CMD_HELP;
	private final String name = "help";

	public Help(WoodCurrency plugin) {
		this.plugin = plugin;
	}

	@Override
	public void dispatch(CommandSender sender, String[] args) {
		if (args.length >= 2 || (args.length != 0 && !args[0].equals("help"))) {
			if (args.length > 1 && args[0].equals("help"))
				getHelp(sender, args[1]);
			else
				getHelp(sender, args[0]);
			return;
		}
		sender.sendMessage(ChatColor.AQUA + "-----[  " + ChatColor.RESET + plugin.getName()
				+ ChatColor.AQUA + " - " + ChatColor.RESET + plugin.version + ChatColor.AQUA
				+ " ]-----");
		for (String s : CommandHandler.getSortedCmdList()) {
			if (!sender.hasPermission("woodcurrency." + s) && sender instanceof Player)
				continue;
			CommandInterface cmd = CommandHandler.get(s);
			if (cmd != null) {
				if (s.equals("info") && !sender.hasPermission("woodcurrency.info.others")
						&& sender instanceof Player)
					sender.sendMessage(helpStringBuilder(cmd.name(), cmd.desc(), ""));
				else
					sender.sendMessage(helpStringBuilder(cmd.name(), cmd.desc(), cmd.usage()));
			}
		}
	}

	public static void getHelp(CommandSender sender, String arg) {
		CommandInterface cmd = CommandHandler.get(arg);
		if (cmd != null
				&& (sender.hasPermission("woodcurrency." + arg) || !(sender instanceof Player))) {
			if (arg.equals("info") && !sender.hasPermission("woodcurrency.info.others")
					&& sender instanceof Player)
				sender.sendMessage(helpStringBuilder(cmd.name(), cmd.desc(), ""));
			else
				sender.sendMessage(helpStringBuilder(cmd.name(), cmd.desc(), cmd.usage()));
			return;
		} else {
			sender.sendMessage(Lang.CMD_UNKNOWN + " " + arg);
			return;
		}
	}

	private static String helpStringBuilder(String n, String d, String u) {
		return ChatColor.translateAlternateColorCodes('&', ChatColor.AQUA + "/wc " + n + " "
				+ ChatColor.GREEN + u + " " + ChatColor.RESET + "- " + d);
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
