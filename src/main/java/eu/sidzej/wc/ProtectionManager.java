package eu.sidzej.wc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;

import eu.sidzej.wc.db.DBUtils;

public class ProtectionManager {
	private static final List<BlockFace> faces = Arrays.asList(BlockFace.EAST, BlockFace.SOUTH,
			BlockFace.WEST, BlockFace.NORTH); // BlockFace.DOWN not needed
	private static final ProtectionManager instance = new ProtectionManager();
	public HashMap<Location, e_protectionType> protectionList;
	public HashMap<Location, WCSign> wcList;

	private ProtectionManager() {
		protectionList = new HashMap<Location, e_protectionType>();
		wcList = new HashMap<Location, WCSign>();
	}

	public static ProtectionManager getInstance() {
		return instance;
	}

	public static void addNew(Location l, BlockFace f) {
		DBUtils.registerShop(l, faces.indexOf(f));
		instance.protectionList.put(l, e_protectionType.SIGN);
		instance.protectionList.put(l.getBlock().getRelative(f).getLocation(),
				e_protectionType.BLOCK);
	}

	public static void addSign(Location l, WCSign s) {
		instance.wcList.put(l, s);
	}

	public static void addFromDB(Location l, int direction) {
		instance.protectionList.put(l, e_protectionType.SIGN);
		int x, z;
		x = (faces.indexOf(BlockFace.EAST) == direction) ? 1
				: (faces.indexOf(BlockFace.WEST) == direction) ? -1 : 0;
		z = (faces.indexOf(BlockFace.SOUTH) == direction) ? 1
				: (faces.indexOf(BlockFace.NORTH) == direction) ? -1 : 0;
		instance.protectionList.put(l.add(x, 0, z), e_protectionType.BLOCK);
	}

	public static boolean isProtected(Location l) {
		return instance.protectionList.containsKey(l);
	}

	public static e_protectionType get(Location l) {
		return instance.protectionList.get(l);
	}

	public static boolean hasWCSign(Location l) {
		return instance.wcList.containsKey(l);
	}

	public static WCSign getSign(Location l) {
		return instance.wcList.get(l);
	}

	public static void remove(Location block) {
		instance.protectionList.remove(block);
		for (Location l : protectionNextTo(block)) {
			deleteSign(l);
		}
	}

	public static void remove(Location sign, Location block) {
		if (!instance.protectionList.containsKey(sign))
			return;
		if (protectionNextTo(block).size() == 1)
			instance.protectionList.remove(block);
		deleteSign(sign);
	}

	private static void deleteSign(Location l) {
		DBUtils.removeShop(l);
		instance.protectionList.remove(l);
		instance.wcList.remove(l);
	}

	public static enum e_protectionType {
		SIGN, BLOCK;
	}

	/**
	 * Get list of locked locations around given location - for block break
	 * 
	 * @param l
	 * @return List of all locked locations around given location
	 */
	private static List<Location> protectionNextTo(Location l) {
		List<Location> list = new ArrayList<Location>();
		int x, y, z;
		for (BlockFace f : faces) {
			Location tmp = l.clone();
			x = (BlockFace.EAST == f) ? 1 : (BlockFace.WEST == f) ? -1 : 0;
			y = (BlockFace.UP == f) ? 1 : (BlockFace.DOWN == f) ? -1 : 0;
			z = (BlockFace.SOUTH == f) ? 1 : (BlockFace.NORTH == f) ? -1 : 0;
			if (isProtected(tmp.add(x, y, z))) {
				list.add(tmp);
			}
		}
		return list;
	}
}
