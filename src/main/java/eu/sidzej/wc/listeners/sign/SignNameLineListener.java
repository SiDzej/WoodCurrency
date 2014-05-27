package eu.sidzej.wc.listeners.sign;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import eu.sidzej.wc.events.SignCreationEvent;
import eu.sidzej.wc.events.SignCreationEvent.e_states;
import eu.sidzej.wc.sign.SignValidator;
import eu.sidzej.wc.utils.Config;

public class SignNameLineListener implements Listener{

	@EventHandler(priority = EventPriority.LOWEST)
	public void SignNameLine(SignCreationEvent e){
		String line = e.getSignLine(SignValidator.NAME_LINE);
		String colorcodeline = line.replaceAll("§", "&");
		
		if(line.equals(Config.SIGN_FIRST_LINE_INPUT) || line.equals(Config.SIGN_FIRST_LINE) ||
				line.equals(colorcodeline))
			e.setLine(SignValidator.NAME_LINE, Config.SIGN_FIRST_LINE);
		else
			e.setState(e_states.BAD_NAME_LINE);
	}

}
