package eu.sidzej.wc.listeners.transaction;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import eu.sidzej.wc.db.TransactionQueue;
import eu.sidzej.wc.events.TransactionEvent;

public class TransactionMonitor implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public static void monitorTransaction(TransactionEvent e) {

		TransactionQueue.addTransaction(e.getPlayer(), e.getItemId(), e.getFinalAmount(),
				e.getType(), e.getFinalPrice(), e.getLocation());
	}
}
