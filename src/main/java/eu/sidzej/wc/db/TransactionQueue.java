package eu.sidzej.wc.db;

import java.io.Closeable;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import eu.sidzej.wc.PlayerManager;
import eu.sidzej.wc.PlayerManager.PlayerData;
import eu.sidzej.wc.WCSign.e_type;
import eu.sidzej.wc.utils.Log;

public class TransactionQueue implements Closeable {

	private static final TransactionQueue instance = new TransactionQueue();
	private final TransactionPeriodicalSave timer;

	private HashMap<Location, Tuple> signs;
	private HashMap<Integer, Transaction> transactions;

	private TransactionQueue() {
		signs = new HashMap<Location, Tuple>();
		transactions = new HashMap<Integer, Transaction>();
		timer = new TransactionPeriodicalSave();
		timer.start();
	}

	public static TransactionQueue getInstance() {
		return instance;
	}

	public static synchronized void addTransaction(Player player, int block, int count,
			e_type type, double price, Location l) {
		PlayerData data = PlayerManager.getPlayerData(player);
		if (instance.signs.containsKey(l))
			if (type.equals(e_type.BUY))
				instance.signs.get(l).x++;
			else
				instance.signs.get(l).y++;
		else {
			instance.signs.put(l, (type.equals(e_type.BUY)) ? new Tuple(1, 0, l) : new Tuple(0, 1,
					l));
		}

		if (instance.transactions.containsKey(data.getID())) {
			Transaction t = instance.transactions.get(data.getID());
			if (t.type.equals(type) && block == t.block) {
				t.count += count;
				t.price += price;
			} else
				instance.transactions.put(data.getID(), new Transaction(data.getID(), block, count,
						type, price));
		} else
			instance.transactions.put(data.getID(), new Transaction(data.getID(), block, count,
					type, price));
	}

	private synchronized void execution() {
		for (Tuple t : instance.signs.values()) {
			DBUtils.updateShop(t.l, t.x, t.y);
		}
		instance.signs.clear();

		for (Transaction t : instance.transactions.values()){
			DBUtils.registerTransaction(t.id, t.block, t.count, t.type, t.price);
			Log.info("transacke");
		}
		instance.transactions.clear();
		PlayerManager.saveAll();

	}

	@Override
	public void close() {
		execution();
	}

	private class TransactionPeriodicalSave extends Thread {
		@Override
		public void run() {
			while (true) {
				try {
					Thread.sleep(120000);

				} catch (final InterruptedException e) {
					Log.error(e.getMessage());
					Log.error("Error in TransactionQueue sleeping thread");
				}
				execution();
			}
		}
	}

	private static class Tuple {
		public int x;
		public int y;
		public Location l;

		public Tuple(int x, int y, Location l) {
			this.x = x;
			this.y = y;
			this.l = l;
		}
	}

	private static class Transaction {
		public int id, block, count;
		public double price;
		public e_type type;

		public Transaction(int id, int block, int count, e_type type, double price) {
			this.id = id;
			this.block = block;
			this.count = count;
			this.type = type;
			this.price = price;
		}
	}

}
