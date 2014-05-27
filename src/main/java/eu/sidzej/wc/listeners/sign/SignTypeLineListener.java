package eu.sidzej.wc.listeners.sign;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import eu.sidzej.wc.WCSign.e_type;
import eu.sidzej.wc.events.SignCreationEvent;
import eu.sidzej.wc.events.SignCreationEvent.e_states;
import eu.sidzej.wc.sign.SignValidator;

public class SignTypeLineListener implements Listener {

	private Boolean flip_prices = false;
	private String price;

	// Normal priority in case of change price line format
	@EventHandler(priority = EventPriority.NORMAL)
	public void SignTypeLine(SignCreationEvent e) {
		String line = e.getSignLine(SignValidator.TYPE_LINE);
		price = e.getSignLine(SignValidator.PRICE_LINE);
		line = line.replaceAll(" ", "").toLowerCase();
		String[] parts = line.split(":");

		String out = "";

		if (parts.length == 1 && !e.getTwoPrices()) {
			if (parts[0].equals("b") || parts[0].equals("buy")) {
				out = "Buy";
				e.setType(e_type.BUY);
				e.setBuyPrice(singlePrice());
			} else if (parts[0].equals("s") || parts[0].equals("sell")) {
				e.setType(e_type.SELL);
				out = "Sell";
				e.setSellPrice(singlePrice());
			}
		} else if (parts.length == 2) {
			if (parts[0].equals("s") || parts[0].equals("sell")) {
				if (parts[1].equals("b") || parts[1].equals("buy")) {
					out = "Buy : Sell";
					flip_prices = true;
					e.setType(e_type.BUYSELL);
					;
				}
			} else if (parts[0].equals("b") || parts[0].equals("buy"))
				if (parts[1].equals("s") || parts[1].equals("sell")) {
					out = "Buy : Sell";
					e.setType(e_type.BUYSELL);
				}
		}
		if (e.getType().equals(e_type.NONE)
				|| (e.getType().equals(e_type.BUYSELL) ^ e.getTwoPrices())) {
			e.setState(e_states.BAD_TYPE_LINE);
			return;
		}

		if (e.getType().equals(e_type.BUYSELL)) {
			if (flip_prices)
				e.setLine(SignValidator.PRICE_LINE,
						flip_prices(e.getSignLine(SignValidator.PRICE_LINE)));
			e.setBuyPrice(doublePrice(0));
			e.setSellPrice(doublePrice(1));
		}
		e.setLine(SignValidator.TYPE_LINE, out);
	}

	private String flip_prices(String l) {
		String[] parts = l.split(":");
		return parts[1].trim() + " : " + parts[0].trim();
	}

	private double singlePrice() {
		return Double.parseDouble(price);
	}

	private double doublePrice(int i) {
		return Double.parseDouble((price.split(":"))[i]);
	}
}
