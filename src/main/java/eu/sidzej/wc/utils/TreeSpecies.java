package eu.sidzej.wc.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum TreeSpecies {
		OAK(1),BIRCH(2),SPRUCE(3),JUNGLE(4),DARK(5),ACACIA(6);
		
		int i;
		private TreeSpecies(int i){
			this.i = i;
		}
		
		public int get(){
			return i;
		}

		public ItemStack toItemStack() {
			return toItemStack(1);
		}

		public ItemStack toItemStack(int amount) {
			ItemStack stack = null;
			if(this.equals(ACACIA) || this.equals(DARK))
				stack = new ItemStack(Material.LOG_2,amount);
			else
				stack = new ItemStack(Material.LOG,amount);
			
			switch(this){
				case JUNGLE:
					stack.setDurability((short)3);
					break;
				case BIRCH:
					stack.setDurability((short)2);
					break;				
				case SPRUCE:
				case DARK:
					stack.setDurability((short)1);
					break;				
				case ACACIA:
				case OAK:
				default:
					stack.setDurability((short)0);
					break;
			}	
			return stack;
		}
}
