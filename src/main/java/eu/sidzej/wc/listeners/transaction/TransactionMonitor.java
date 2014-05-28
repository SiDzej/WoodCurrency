package eu.sidzej.wc.listeners.transaction;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import eu.sidzej.wc.PlayerManager;
import eu.sidzej.wc.PlayerManager.PlayerData;
import eu.sidzej.wc.events.TransactionEvent;

public class TransactionMonitor implements Listener{

	@EventHandler(priority = EventPriority.MONITOR)
	public static void monitorTransaction(TransactionEvent e){
		PlayerData data = PlayerManager.getPlayerData(e.getPlayer());
		e.getPlayer().updateInventory();
		data.addCount(e.getFinalAmount());
	}
}
