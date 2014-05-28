package eu.sidzej.wc.listeners;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import com.avaje.ebean.Transaction;

import eu.sidzej.wc.PlayerManager;
import eu.sidzej.wc.ProtectionManager;
import eu.sidzej.wc.WCSign;
import eu.sidzej.wc.db.DBUtils;
import eu.sidzej.wc.events.TransactionEvent;
import eu.sidzej.wc.events.TransactionPrepareEvent;

public class PlayerListener implements Listener {

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		PlayerManager.getPlayerData(p.getUniqueId());
		/*
		 * p.getInventory().addItem(TreeSpecies.OAK.toItemStack());
		 * p.getInventory().addItem(TreeSpecies.BIRCH.toItemStack(5));
		 * p.getInventory().addItem(TreeSpecies.ACACIA.toItemStack(5));
		 * p.getInventory().addItem(TreeSpecies.DARK.toItemStack(5));
		 * p.getInventory().addItem(TreeSpecies.JUNGLE.toItemStack(5));
		 * p.getInventory().addItem(TreeSpecies.SPRUCE.toItemStack(5));
		 */
	}

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onPlayerJoin(PlayerInteractEvent event) {
		if (ProtectionManager.isProtected(event.getClickedBlock().getLocation()))
			event.getPlayer().sendMessage("Protected by WC!");

		Block b = event.getClickedBlock();
		Player p = event.getPlayer();
		Location l = b.getLocation();

		// not a sign| item in hand while sneak
		if (b == null || b.getType() != Material.WALL_SIGN || !ProtectionManager.isProtected(l))
			return;
		if (p.isSneaking() && p.getItemInHand() != null
				&& !p.getItemInHand().getType().equals(Material.AIR))
			return;
		
		if (event.getPlayer().getGameMode() == GameMode.CREATIVE) {
			if (event.getAction() == Action.RIGHT_CLICK_AIR
					|| event.getAction() == Action.RIGHT_CLICK_BLOCK)
				p.sendMessage("Hey you! No cheating in creative!"); // TODO lang
			return;
		}

		WCSign sign = ProtectionManager.getSign(l);
			
		TransactionPrepareEvent preTrans = new TransactionPrepareEvent(p,sign,event.getAction());
		
		if(preTrans.isCancelled())
			return;
		
		TransactionEvent trans = new TransactionEvent();
			

		// setup transaction
		Transaction trans = new Transaction(event.getPlayer(), sign, event.getAction());
		if (!trans.successful())
			return;

		if (trans.getAmount() == 0) {
			if (trans.getType() == buysellenum.BUY)
				p.sendMessage("Not enough money or no space in inventory.");
			else
				p.sendMessage("You have nothing to sell.");
		} else {
			DBUtils.registerTransaction(1, sign.getItemId(), trans.getAmount(),
					(trans.getType() == buysellenum.BUY) ? 0 : 1);
			if (trans.getType() == buysellenum.BUY)
				p.sendMessage("You just bought " + trans.getAmount() + " pieces of "
						+ sign.getItemName() + " Wood for " + Eco.format(trans.getPrice()));
			else
				p.sendMessage("You just sold " + trans.getAmount() + " pieces of "
						+ sign.getItemName() + " Wood for " + Eco.format(trans.getPrice()));
		}

		// canceling only place event...
		if (event.getAction() == Action.LEFT_CLICK_BLOCK
				|| event.getAction() == Action.LEFT_CLICK_AIR)
			return;
		event.setCancelled(true);
	}

}
