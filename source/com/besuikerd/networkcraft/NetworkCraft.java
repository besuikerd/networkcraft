package com.besuikerd.networkcraft;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.SidedProxy;

@Mod(modid = NetworkCraft.ID)
public class NetworkCraft {
	
	public static final String ID = "NetworkCraft";
	
	@SidedProxy(clientSide = ClientProxy.CLS, serverSide = CommonProxy.CLS)
	public static CommonProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e){
		proxy.registerBlocks();
		proxy.registerTileEntities();
		proxy.registerRenderers();
	}
}
