package eu.sidzej.wc.listeners.sign;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import eu.sidzej.wc.events.SignCreationEvent;
import eu.sidzej.wc.events.SignCreationEvent.e_states;
import eu.sidzej.wc.sign.SignValidator;

public class SignPriceLineListener implements Listener {
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void SignPriceLine(SignCreationEvent e){
		String line = e.getSignLine(SignValidator.PRICE_LINE);
		String[] parts = line.toLowerCase().replaceAll(" ", "").split(":");
		
		String out = "";
		Boolean fail = false;
		
		if(parts.length == 1)
			try {
	            Double.parseDouble(parts[0]);
	            out = parts[0];
	        } catch (NumberFormatException er) {
	            fail = true;
	        }		
		else if(parts.length == 2)
			try {
	            Double.parseDouble(parts[0]);
	            Double.parseDouble(parts[1]);
	            out = parts[0] + " : " + parts[1];
	            e.setTwoPrices(true);
	        } catch (NumberFormatException er) {
	            fail = true;
	        }
		else 
			fail = true;
		
		e.setLine(SignValidator.PRICE_LINE, out);
		if(fail) e.setState(e_states.BAD_PRICE_LINE);
	}
}
