package eu.sidzej.wc.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.material.Attachable;

import eu.sidzej.wc.WCSign;
import eu.sidzej.wc.WoodCurrency;
import eu.sidzej.wc.events.ShopCreatedEvent;
import eu.sidzej.wc.events.SignCreationEvent;

public class SignChangeListener implements Listener{
	
	@EventHandler
	public void onSignTextChange(SignChangeEvent event) { 
		Player p = event.getPlayer();
		Block b = event.getBlock();
		
		if(!WCSign.isValidPreparedSign(event.getLines())){
			p.sendMessage("zruseno chyba - not valid sign");
			return;
		}
		
		Block relative = b.getRelative(((Attachable) b.getState().getData()).getAttachedFace());
		
		if(!b.getType().equals(Material.WALL_SIGN) 
				|| !relative.getType().isBlock() || relative.getType().hasGravity()){
			p.sendMessage("zruseno chyba - block/gravity/wallsign");
			b.breakNaturally();
			event.setCancelled(true);
			return;
		}
		SignCreationEvent create = new SignCreationEvent(event.getPlayer(), event.getLines());
        WoodCurrency.callEvent(create);

        if (create.isCancelled()) {
        	b.breakNaturally();
            return;
        }

        for (byte i = 0; i < event.getLines().length; ++i) {
            event.setLine(i, create.getSignLine(i));
        }

        ShopCreatedEvent finished = new ShopCreatedEvent(create.getPlayer(),b, create.getSignLines());
        WoodCurrency.callEvent(finished);
		
		// registrace 
		// zruseni cedule
	}

}
