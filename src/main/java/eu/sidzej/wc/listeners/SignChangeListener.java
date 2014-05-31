package eu.sidzej.wc.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.material.Attachable;
import org.bukkit.material.Sign;

import eu.sidzej.wc.ProtectionManager;
import eu.sidzej.wc.WoodCurrency;
import eu.sidzej.wc.config.Lang;
import eu.sidzej.wc.events.ShopCreatedEvent;
import eu.sidzej.wc.events.SignCreationEvent;
import eu.sidzej.wc.sign.SignValidator;

public class SignChangeListener implements Listener {

	@EventHandler
	public void onSignTextChange(SignChangeEvent event) {
		Player p = event.getPlayer();
		Block b = event.getBlock();
		Block relative = b.getRelative(((Attachable) b.getState().getData()).getAttachedFace());

		if (ProtectionManager.isProtected(b.getLocation())
				&& !p.hasPermission("woodcurrency.createshop")) {
			p.sendMessage(Lang.NO_PERMISSION);
			event.setCancelled(true);
			return;
		}

		if (!SignValidator.isValidPreparedSign(event.getLines())) {
			ProtectionManager.remove(b.getLocation(), relative.getLocation());
			return;
		}

		if (!b.getType().equals(Material.WALL_SIGN) || !relative.getType().isBlock()
				|| relative.getType().hasGravity()) {
			p.sendMessage(Lang.CANT_CREATE_SHOP);
			b.breakNaturally();
			event.setCancelled(true);
			return;
		}
		SignCreationEvent create = new SignCreationEvent(event.getPlayer(), event.getLines());
		WoodCurrency.callEvent(create);

		if (create.isCancelled()) {
			b.breakNaturally();
			ProtectionManager.remove(b.getLocation(), relative.getLocation());
			return;
		}

		for (byte i = 0; i < event.getLines().length; ++i) {
			event.setLine(i, create.getLine(i));
		}

		ShopCreatedEvent finished = new ShopCreatedEvent(create.getPlayer(), b,
				create.getSignLines());
		WoodCurrency.callEvent(finished);
		ProtectionManager
				.addNew(b.getLocation(), ((Sign) b.getState().getData()).getAttachedFace());
	}

}
