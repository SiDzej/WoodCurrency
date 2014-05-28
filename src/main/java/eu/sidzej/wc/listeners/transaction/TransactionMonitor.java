package eu.sidzej.wc.listeners.transaction;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import eu.sidzej.wc.PlayerManager;
import eu.sidzej.wc.PlayerManager.PlayerData;
import eu.sidzej.wc.db.DBUtils;
import eu.sidzej.wc.events.TransactionEvent;

public class TransactionMonitor implements Listener{

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.MONITOR)
	public static void monitorTransaction(TransactionEvent e){
		PlayerData data = PlayerManager.getPlayerData(e.getPlayer());
		e.getPlayer().updateInventory(); // TODO bukkit depricated
		data.addCount(e.getFinalAmount());
		
		DBUtils.registerTransaction(e.getPlayer(), e.getItemId(), e.getFinalAmount(), e.getType());
	}
}
