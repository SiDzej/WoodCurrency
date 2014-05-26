package eu.sidzej.wc.listeners.sign;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import eu.sidzej.wc.WCSign;
import eu.sidzej.wc.events.SignCreationEvent;
import eu.sidzej.wc.events.SignCreationEvent.E_States;

public class SignPriceLineListener implements Listener {
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void SignPriceLine(SignCreationEvent e){
		String line = e.getSignLine(WCSign.PRICE_LINE);
		String[] parts = line.toLowerCase().split(":");
		
		String out = "";
		Boolean fail = false;
		
		if(parts.length == 1)
			try {
	            Double.parseDouble(parts[0]);
	            out = parts[0].trim();
	        } catch (NumberFormatException er) {
	            fail = true;
	        }		
		else if(parts.length == 2)
			try {
	            Double.parseDouble(parts[0]);
	            Double.parseDouble(parts[1]);
	            out = parts[0].trim() + " : " + parts[1].trim();
	            e.setTwoPrices(true);
	        } catch (NumberFormatException er) {
	            fail = true;
	        }
		else 
			fail = true;
		
		e.setLine(WCSign.PRICE_LINE, out);
		if(fail) e.setState(E_States.BAD_PRICE_LINE);
	}
}
