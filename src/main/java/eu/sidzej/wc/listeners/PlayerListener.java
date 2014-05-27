package eu.sidzej.wc.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import eu.sidzej.wc.ProtectionManager;
import eu.sidzej.wc.utils.TreeSpecies;

public class PlayerListener implements Listener{
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		p.sendMessage("Baf "+p.getName()+" - " + p.getUniqueId() );
		
		/*
		p.getInventory().addItem(TreeSpecies.OAK.toItemStack());
		p.getInventory().addItem(TreeSpecies.BIRCH.toItemStack(5));
		p.getInventory().addItem(TreeSpecies.ACACIA.toItemStack(5));
		p.getInventory().addItem(TreeSpecies.DARK.toItemStack(5));
		p.getInventory().addItem(TreeSpecies.JUNGLE.toItemStack(5));
		p.getInventory().addItem(TreeSpecies.SPRUCE.toItemStack(5));*/
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onPlayerJoin(PlayerInteractEvent event) {
		if(ProtectionManager.isProtected(event.getClickedBlock().getLocation()))
			event.getPlayer().sendMessage("Protected by WC!");
	}

}
