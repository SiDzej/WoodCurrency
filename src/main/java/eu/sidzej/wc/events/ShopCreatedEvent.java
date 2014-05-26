package eu.sidzej.wc.events;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ShopCreatedEvent extends Event {
	private static final HandlerList handlers = new HandlerList();
	private Player p;
	private Block b;
	private String[] lines;

	public ShopCreatedEvent(Player player, Block b, String[] signLines) {
		p = player;
		this.b = b;
		this.lines = signLines;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	public Player getPlayer() {
		return p;
	}

	public String[] getLines() {
		return lines;
	}

	public String getPositionString() {
		return "" + b.getWorld() +","+ b.getX() +","+ b.getY() +","+ b.getZ();
	}

}
