package eu.sidzej.wc.listeners.sign;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import eu.sidzej.wc.events.SignCreationEvent;
import eu.sidzej.wc.events.SignCreationEvent.e_states;
import eu.sidzej.wc.sign.SignValidator;
import eu.sidzej.wc.utils.Config;

public class SignItemLineListener implements Listener {

	@EventHandler(priority = EventPriority.LOWEST)
	public void SignTypeLine(SignCreationEvent e) {
		String line = e.getSignLine(SignValidator.ITEM_LINE).toLowerCase();
		String[] parts = line.split(" ");

		if (parts.length > 3) {
			e.setState(e_states.BAD_ITEM_LINE);
			return;
		}

		String out = "";
		String wood = parts[parts.length - 1].trim();
		String type = parts[0].trim();
		if (parts.length == 3) {
			try {
				Double.parseDouble(parts[0]);
				type = parts[1].trim();
			} catch (NumberFormatException er) {
				type += parts[1].trim();
			}
		}

		if (!wood.equals("wood")){
			e.setState(e_states.BAD_ITEM_LINE);
			return;
		}
			

		if (type.equals("oak"))
			out = "Oak";
		else if (type.equals("birch"))
			out = "Birch";
		else if (type.equals("spruce"))
			out = "Spruce";
		else if (type.equals("acacia"))
			out = "Acacia";
		else if (type.equals("darkoak"))
			out = "DarkOak";
		else if (type.equals("jungle"))
			out = "Jungle";
		else
			e.setState(e_states.BAD_ITEM_LINE);

		out = Config.STACK_SIZE + " " + out + " " + "Wood";
		e.setLine(SignValidator.ITEM_LINE, out);
	}

}
