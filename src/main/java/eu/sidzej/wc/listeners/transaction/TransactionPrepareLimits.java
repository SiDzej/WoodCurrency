package eu.sidzej.wc.listeners.transaction;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import eu.sidzej.wc.PlayerManager;
import eu.sidzej.wc.PlayerManager.PlayerData;
import eu.sidzej.wc.WCSign.e_type;
import eu.sidzej.wc.events.TransactionPrepareEvent;
import eu.sidzej.wc.events.TransactionPrepareEvent.e_states;
import eu.sidzej.wc.utils.PlayerUtils;

public class TransactionPrepareLimits implements Listener {

	@EventHandler(priority = EventPriority.HIGH)
	public static void UpdatePlayerData(TransactionPrepareEvent e) {
		PlayerData data = PlayerManager.getPlayerData(e.getPlayer());

		if (PlayerUtils.checkDailyLimits(data))
			if (e.getState().equals(e_states.DAY_LIMIT))
				e.setState(e_states.OK);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public static void TransactionsLimits(TransactionPrepareEvent e) {
		PlayerData data = PlayerManager.getPlayerData(e.getPlayer());
		if (e.getType().equals(e_type.SELL))
			if (!(data.getItemLeft() >= 1))
				e.setState(e_states.DAY_LIMIT);
	}

}
