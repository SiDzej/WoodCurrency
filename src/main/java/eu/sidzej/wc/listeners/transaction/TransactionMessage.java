package eu.sidzej.wc.listeners.transaction;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import eu.sidzej.wc.WCSign.e_type;
import eu.sidzej.wc.config.Lang;
import eu.sidzej.wc.events.TransactionEvent;
import eu.sidzej.wc.utils.EconomyUtils;

public class TransactionMessage implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public static void SendMsg(TransactionEvent e) {
		String out = "";
		if (e.getType().equals(e_type.BUY))
			out = Lang.BOUGHT;
		if (e.getType().equals(e_type.SELL))
			out = Lang.SOLD;

		out.replace("{amount}", ""+e.getFinalAmount());
		out.replace("{item}", e.getItemName());
		out.replace("{price}", EconomyUtils.format(e.getFinalPrice()));
		
		e.getPlayer().sendMessage(out);
	}
}
