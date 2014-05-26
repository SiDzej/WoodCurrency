package eu.sidzej.wc.listeners.sign;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import eu.sidzej.wc.WCSign;
import eu.sidzej.wc.events.SignCreationEvent;
import eu.sidzej.wc.events.SignCreationEvent.E_States;

public class SignTypeLineListener implements Listener {

	private Boolean flip_prices = false;

	// Normal priority in case of change price line format
	@EventHandler(priority = EventPriority.NORMAL)
	public void SignTypeLine(SignCreationEvent e) {
		String line = e.getSignLine(WCSign.TYPE_LINE);
		line = line.replaceAll(" ", "").toLowerCase();		
		String[] parts = line.split(":");

		String out = "";
		Boolean fail = false;

		if (parts.length == 1)
			if (parts[0].equals("b") || parts[0].equals("buy"))
				out = "Buy";
			else if (parts[0].equals("s") || parts[0].equals("sell"))
				out = "Sell";
			else
				fail = true;
		else if (parts.length == 2) {
			if (parts[0].equals("s") || parts[0].equals("sell"))
				if (parts[1].equals("b") || parts[1].equals("buy")) {
					out = "Buy : Sell";
					flip_prices = true;
				} else
					fail = true;
			else if (parts[0].equals("b") || parts[0].equals("buy"))
				if (parts[1].equals("s") || parts[1].equals("sell"))
					out = "Buy : Sell";
				else
					fail = true;
			else
				fail = true;
		} else
			fail = true;

		if (flip_prices)
			if (e.getTwoPrices())
				e.setLine(WCSign.PRICE_LINE, flip_prices(e.getSignLine(WCSign.PRICE_LINE)));
			else {
				e.setState(E_States.BAD_TYPE_TO_PRICE_LINES);
				return;
			}
		e.setLine(WCSign.TYPE_LINE, out);
		if (fail)
			e.setState(E_States.BAD_TYPE_LINE);
	}

	private String flip_prices(String l) {
		String[] parts = l.split(":");
		return parts[1].trim() + " : " + parts[0].trim();
	}

}
