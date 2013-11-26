package nl.besuikerd.networkcraft.core;

import net.minecraftforge.common.Configuration;


public class NCConfig {
	
	public static int block_router;
	public static int block_cable;
	
	public static void init(Configuration cfg){
		block_router = cfg.getBlock("device.router", 650).getInt();
		block_cable = cfg.getBlock("cable", 651).getInt();
	}
}
