package eu.sidzej.wc.listeners.sign;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import eu.sidzej.wc.WCSign;
import eu.sidzej.wc.events.SignCreationEvent;
import eu.sidzej.wc.events.SignCreationEvent.E_States;
import eu.sidzej.wc.utils.Config;

public class SignNameLineListener implements Listener{

	@EventHandler(priority = EventPriority.LOWEST)
	public void SignNameLine(SignCreationEvent e){
		String line = e.getSignLine(WCSign.NAME_LINE);
		String colorcodeline = line.replaceAll("§", "&");
		
		if(line.equals(Config.SIGN_FIRST_LINE_INPUT) || line.equals(Config.SIGN_FIRST_LINE) ||
				line.equals(colorcodeline))
			e.setLine(WCSign.NAME_LINE, Config.SIGN_FIRST_LINE);
		else
			e.setState(E_States.BAD_NAME_LINE);
	}

}
