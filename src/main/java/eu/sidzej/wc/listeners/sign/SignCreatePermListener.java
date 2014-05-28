package eu.sidzej.wc.listeners.sign;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import eu.sidzej.wc.events.SignCreationEvent;
import eu.sidzej.wc.events.SignCreationEvent.e_states;

public class SignCreatePermListener implements Listener {

	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void hasPermission(SignCreationEvent e){
		if(!e.getPlayer().hasPermission("woodcurrency.createshop"))
			e.setState(e_states.NO_PERMISSIONS);
	}
}
