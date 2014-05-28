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

import eu.sidzej.wc.PlayerManager;
import eu.sidzej.wc.ProtectionManager;
import eu.sidzej.wc.WCSign;
import eu.sidzej.wc.WoodCurrency;
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
		WoodCurrency.callEvent(preTrans);
		
		if(preTrans.isCancelled())
			return;
		
		TransactionEvent transaction = new TransactionEvent(p, preTrans.getType(), sign);
		WoodCurrency.callEvent(transaction);
		
		// canceling only place event...
		if (event.getAction() == Action.LEFT_CLICK_BLOCK
				|| event.getAction() == Action.LEFT_CLICK_AIR)
			return;
		event.setCancelled(true);
	}

}
