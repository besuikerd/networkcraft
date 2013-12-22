package nl.besuikerd.networkcraft;

import net.minecraftforge.common.Configuration;


public class NCConfig {
	
	public static int block_cable;
	public static int block_masterNode;
	public static int block_inventory;
	
	
	public static void init(Configuration cfg){
		block_cable = cfg.getBlock("cable", 651).getInt();
		block_masterNode = cfg.getBlock("masterNode", 652).getInt();
		block_inventory = cfg.getBlock("inventory", 653).getInt();
	}
}
