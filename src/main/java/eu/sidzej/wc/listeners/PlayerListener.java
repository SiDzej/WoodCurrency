package eu.sidzej.wc.listeners;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import eu.sidzej.wc.PlayerManager;
import eu.sidzej.wc.PlayerManager.PlayerData;
import eu.sidzej.wc.ProtectionManager;
import eu.sidzej.wc.WCSign;
import eu.sidzej.wc.WoodCurrency;
import eu.sidzej.wc.config.Lang;
import eu.sidzej.wc.db.DBUtils;
import eu.sidzej.wc.events.TransactionEvent;
import eu.sidzej.wc.events.TransactionPrepareEvent;
import eu.sidzej.wc.sign.SignValidator;
import eu.sidzej.wc.utils.Log;
import eu.sidzej.wc.utils.PlayerUtils;

public class PlayerListener implements Listener {

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		PlayerManager.playerJoin(p.getUniqueId());
		PlayerUtils.checkDailyLimits(PlayerManager.getPlayerData(p), false);
	}

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player p = event.getPlayer();
		PlayerData data = PlayerManager.getPlayerData(p.getUniqueId());
		DBUtils.UpdatePlayer(data);

		PlayerManager.remove(p);
	}

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onPlayerInteract(PlayerInteractEvent event) {
		Block b = event.getClickedBlock();
		Player p = event.getPlayer();
		Location l = b.getLocation();
		
		// not a sign| item in hand while sneak
		if (b == null || b.getType() != Material.WALL_SIGN || !ProtectionManager.isProtected(l)) {
			if (b != null && SignValidator.isValidPreparedSign(((Sign) b.getState()).getLines())) {
				p.sendMessage(Lang.FAKE_SIGN);
				Log.info("&cFake shop sign found at " + l.getWorld().getName() + "," + l.getX() + ","
						+ l.getY() + "," + l.getZ() + " by " + p.getName() + ".");
				b.breakNaturally();
			}
			return;
		}
		if (p.isSneaking() && p.getItemInHand() != null
				&& !p.getItemInHand().getType().equals(Material.AIR))
			return;

		if (event.getPlayer().getGameMode() == GameMode.CREATIVE) {
			if (event.getAction() == Action.RIGHT_CLICK_AIR
					|| event.getAction() == Action.RIGHT_CLICK_BLOCK)
				p.sendMessage("Hey you! No cheating in creative!");
			return;
		}

		WCSign sign = ProtectionManager.getSign(l);
		
		if(sign == null){
			p.sendMessage(Lang.FAKE_SIGN);
			b.breakNaturally();
			Log.error("Someone change sign at " + l.getWorld().getName() + "," + l.getX() + "," + l.getY()
				+ "," + l.getZ() + " clicked by " + p.getName() + ". Shop removed.");
			event.setCancelled(true);
			return;
		}

		TransactionPrepareEvent preTrans = new TransactionPrepareEvent(p, sign, event.getAction());
		WoodCurrency.callEvent(preTrans);

		if (preTrans.isCancelled())
			return;

		TransactionEvent transaction = new TransactionEvent(p, preTrans.getType(), sign, l);
		WoodCurrency.callEvent(transaction);

		// canceling only place event...
		if (event.getAction() == Action.LEFT_CLICK_BLOCK
				|| event.getAction() == Action.LEFT_CLICK_AIR)
			return;
		event.setCancelled(true);
	}

}
