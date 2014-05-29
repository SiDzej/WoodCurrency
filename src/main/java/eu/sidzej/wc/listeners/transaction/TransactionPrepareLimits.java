package eu.sidzej.wc.listeners.transaction;

import java.util.Calendar;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import eu.sidzej.wc.PlayerManager;
import eu.sidzej.wc.PlayerManager.PlayerData;
import eu.sidzej.wc.db.DBUtils;
import eu.sidzej.wc.events.TransactionPrepareEvent;
import eu.sidzej.wc.events.TransactionPrepareEvent.e_states;

public class TransactionPrepareLimits implements Listener {
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public static void UpdatePlayerData(TransactionPrepareEvent e) {
		PlayerData data = PlayerManager.getPlayerData(e.getPlayer());

		Calendar c = (Calendar) Calendar.getInstance().clone();
		if (c.before(data.getDate()))
			return;
		data.getDate().add(Calendar.DATE, 1);
		if (c.before(data.getDate())) {
			data.incrementTier();
		} else {
			data.decrementTier();
		}
		data.resetDay();
		
		if(e.getState().equals(e_states.DAY_LIMIT))
			e.setState(e_states.OK);
		
		DBUtils.UpdatePlayer(e.getPlayer().getUniqueId(), data);
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public static void TransactionsLimits(TransactionPrepareEvent e){
		PlayerData data = PlayerManager.getPlayerData(e.getPlayer());
		if(!(data.getItemLeft() >= 1))
			e.setState(e_states.DAY_LIMIT);
	}

}
