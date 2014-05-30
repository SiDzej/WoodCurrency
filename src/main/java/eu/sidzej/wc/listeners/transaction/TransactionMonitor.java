package eu.sidzej.wc.listeners.transaction;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import eu.sidzej.wc.db.TransactionQueue;
import eu.sidzej.wc.events.TransactionEvent;

public class TransactionMonitor implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.MONITOR)
	public static void monitorTransaction(TransactionEvent e) {
		e.getPlayer().updateInventory(); // TODO bukkit depricated

		TransactionQueue.addTransaction(e.getPlayer(), e.getItemId(), e.getFinalAmount(),
				e.getType(), e.getFinalPrice(), e.getLocation());
	}
}
