package eu.sidzej.wc.listeners.sign;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import eu.sidzej.wc.events.SignCreationEvent;
import eu.sidzej.wc.events.SignCreationEvent.e_states;
import eu.sidzej.wc.utils.Permissions;

public class SignCreatePermListener implements Listener {

	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void hasPermission(SignCreationEvent e){
		if(!Permissions.placeSign(e.getPlayer()))
			e.setState(e_states.NO_PERMISSIONS);
	}
}
