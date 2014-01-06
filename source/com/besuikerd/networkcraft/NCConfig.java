package com.besuikerd.networkcraft;

import net.minecraftforge.common.Configuration;


public class NCConfig {
	
	public static int block_cable;
	public static int block_masterNode;
	public static int block_endPoint;
	
	public static int block_inventory;
	public static int block_gui;
	
	
	public static void init(Configuration cfg){
		block_cable = cfg.getBlock("cable", 651).getInt();
		block_masterNode = cfg.getBlock("masterNode", 652).getInt();
		block_endPoint = cfg.getBlock("endPoint", 653).getInt();
		block_inventory = cfg.getBlock("inventory", 654).getInt();
		block_gui = cfg.getBlock("gui", 655).getInt();
	}
}
