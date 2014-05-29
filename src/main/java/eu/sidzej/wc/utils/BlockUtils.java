package eu.sidzej.wc.utils;

import org.bukkit.inventory.ItemStack;

public class BlockUtils {

	public static String getItemStackName(String s) {
		TreeSpecies tree = getTree(s);
		String w = new String(" Wood");

		switch (tree) {
			case OAK:
				return "Oak" + w;
			case JUNGLE:
				return "Jungle" + w;
			case SPRUCE:
				return "Spruce" + w;
			case DARK:
				return "DarkOak" + w;
			case ACACIA:
				return "Acacia" + w;
			case BIRCH:
				return "Birch" + w;
			default:
				return null;
		}
	}

	public static ItemStack getItemStack(String s) {
		return getTree(s).toItemStack();
	}

	public static int getItemStackId(String s){
		return getTree(s).get();
	}
	
	private static TreeSpecies getTree(String s){
		String[] parts = s.toLowerCase().split(" ");

		if (parts.length > 3)
			return null;
		
		TreeSpecies out = null;
		String wood = parts[parts.length - 1].trim();
		String type = parts[0].trim();
		if (parts.length == 3) {
			try {
				Double.parseDouble(parts[0]);
				type = parts[1].trim();
			} catch (NumberFormatException er) {
				type += parts[1].trim();
			}
		}
		if (!wood.equals("wood")){
			return null;
		}
		
		if (type.equals("oak"))
			out = TreeSpecies.OAK;
		else if (type.equals("birch"))
			out = TreeSpecies.BIRCH;
		else if (type.equals("spruce"))
			out = TreeSpecies.SPRUCE;
		else if (type.equals("acacia"))
			out = TreeSpecies.ACACIA;
		else if (type.equals("darkoak"))
			out = TreeSpecies.DARK;
		else if (type.equals("jungle"))
			out = TreeSpecies.JUNGLE;
		else
			return null;
		
		return out;
	}
}
