package eu.sidzej.wc.listeners;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.material.Sign;

import eu.sidzej.wc.ProtectionManager;
import eu.sidzej.wc.ProtectionManager.e_protectionType;
import eu.sidzej.wc.utils.Log;
import eu.sidzej.wc.utils.Permissions;

public class BlockBreakListener implements Listener {

	/**
	 * Block break event by player
	 * 
	 * @param e
	 *            event
	 */
	@EventHandler(priority = EventPriority.NORMAL)
	public void onBlockBreak(BlockBreakEvent e) {
		Block b = e.getBlock();
		Location l = b.getLocation();
		Player p = e.getPlayer();

		if (!ProtectionManager.isProtected(l))
			return;

		if (!Permissions.removeSign(p)) {
			e.setCancelled(true);
			p.sendMessage("no permission");	// TODO lang
			return;
		}

		if (!ProtectionManager.get(l).equals(e_protectionType.BLOCK)) {
			ProtectionManager.remove(l,
					b.getRelative(((Sign) b.getState().getData()).getAttachedFace()).getLocation());
		} else {
			ProtectionManager.remove(l);
		}

		p.sendMessage("WC shop(s) has been destroyed."); // TODO Lang
		Log.info("WoodCurreny shop at "+ l.getWorld() +","+ l.getX() +","+ l.getY() +","+ l.getZ()+ " destroyed.");
		b.breakNaturally();
	}

	/**
	 * Removes WC block and sign from Explosion block break
	 * 
	 * @param e
	 *            event
	 */
	@EventHandler(priority = EventPriority.HIGH)
	public void explosion(EntityExplodeEvent e) {
		Iterator<Block> i = e.blockList().iterator();
		List<Block> list = new ArrayList<Block>();
		while (i.hasNext()) {
			Block b = i.next();
			if (ProtectionManager.isProtected(b.getLocation())) {
				list.add(b);
			}

		}
		e.blockList().removeAll(list);
	}

	/**
	 * Prevent sticky piston to retract if it can break shop
	 * 
	 * @param e
	 *            event
	 */
	@EventHandler(priority = EventPriority.HIGH)
	public void pistonRetract(BlockPistonRetractEvent e) {
		if (!e.isSticky())
			return;
		if (ProtectionManager.isProtected(e.getRetractLocation()))
			e.setCancelled(true);
	}

	/**
	 * Prevent piston to extend if there is WC shop in moved blocks
	 * 
	 * @param e
	 *            event
	 */
	@EventHandler(priority = EventPriority.HIGH)
	public void pistonExtend(BlockPistonExtendEvent e) {
		for (Block b : e.getBlocks()) {
			if (ProtectionManager.isProtected(b.getLocation())) {
				e.setCancelled(true);
				return;
			}
		}

	}

}
