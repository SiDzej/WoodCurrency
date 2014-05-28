package eu.sidzej.wc.listeners.sign;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import eu.sidzej.wc.events.SignCreationEvent;
import eu.sidzej.wc.events.SignCreationEvent.e_states;
import eu.sidzej.wc.sign.SignValidator;
import eu.sidzej.wc.utils.BlockUtils;
import eu.sidzej.wc.utils.Config;

public class SignItemLineListener implements Listener {

	@EventHandler(priority = EventPriority.LOWEST)
	public void SignTypeLine(SignCreationEvent e) {
		String out = BlockUtils.getItemStackName(e.getLine(SignValidator.ITEM_LINE));

		if(out == null)
			e.setState(e_states.BAD_ITEM_LINE);
		
		out = Config.STACK_SIZE + " " + out;
		e.setLine(SignValidator.ITEM_LINE, out);
	}

}
