package eu.sidzej.wc.listeners.transaction;

import java.util.Map;
import java.util.WeakHashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import eu.sidzej.wc.events.TransactionPrepareEvent;
import eu.sidzej.wc.events.TransactionPrepareEvent.e_states;

public class TransactionDelayer implements Listener {
	private final Map<Player, Long> TIME_OF_LATEST_CLICK = new WeakHashMap<Player, Long>();

	@EventHandler(priority = EventPriority.LOWEST)
	public void onClick(TransactionPrepareEvent event) {
		if (event.isCancelled()) {
			return;
		}

		if (TIME_OF_LATEST_CLICK.containsKey(event.getPlayer())
				&& (System.currentTimeMillis() - TIME_OF_LATEST_CLICK.get(event.getPlayer())) < 250) {
			event.setState(e_states.TOO_FAST_CLICK);
			return;
		}

		TIME_OF_LATEST_CLICK.put(event.getPlayer(), System.currentTimeMillis());
	}
}
