package com.besuikerd.networkcraft;

import com.besuikerd.core.BLogger;
import com.besuikerd.core.gui.GuiHandlerBesu;
import com.besuikerd.core.packet.PacketBesu;
import com.besuikerd.core.packet.PacketHandlerBesu;
import com.besuikerd.networkcraft.block.BlockCable;
import com.besuikerd.networkcraft.block.BlockEndPoint;
import com.besuikerd.networkcraft.block.BlockMasterNode;
import com.besuikerd.networkcraft.block.BlockTestGui;
import com.besuikerd.networkcraft.block.BlockTestInventory;
import com.besuikerd.networkcraft.graph.TileEntityEndPoint;
import com.besuikerd.networkcraft.graph.TileEntityMasterNode;
import com.besuikerd.networkcraft.tileentity.TileEntityCable;
import com.besuikerd.networkcraft.tileentity.TileEntityTestGui;
import com.besuikerd.networkcraft.tileentity.TileEntityTestInventory;

import net.minecraft.block.Block;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkMod.SidedPacketHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid=Reference.MOD_ID, name=Reference.MOD_NAME, version=Reference.MOD_VERSION)
@NetworkMod(
	clientSideRequired=true, 
	serverSideRequired = false, 
	serverPacketHandlerSpec = @SidedPacketHandler(channels={PacketBesu.DEFAULT_CHANNEL}, packetHandler = PacketHandlerBesu.class),
	clientPacketHandlerSpec = @SidedPacketHandler(channels={PacketBesu.DEFAULT_CHANNEL}, packetHandler = PacketHandlerBesu.class)
)
public class NetworkCraft{
	
	public static boolean DEBUG_MODE = true;
	
	public static Block blockCable;
	public static Block blockMasterNode;
	public static Block blockEndPoint;
	
	public static Block blockInventory;
	public static Block blockGui;
	
	
	@Instance()
	public static NetworkCraft instance;
	
	@SidedProxy(clientSide=Reference.PROXY_CLIENT, serverSide=Reference.PROXY_SERVER)
	public static CommonProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e){
		//initialize logger
		BLogger.init();
		
		//setup configuration
		Configuration cfg = new Configuration(e.getSuggestedConfigurationFile());
		NCConfig.init(cfg);
		cfg.save();
		
		instantiateBlocks();
		
		//register blocks
		GameRegistry.registerBlock(blockCable, blockCable.getUnlocalizedName());
		GameRegistry.registerBlock(blockMasterNode, blockMasterNode.getUnlocalizedName());
		GameRegistry.registerBlock(blockInventory, blockInventory.getUnlocalizedName());
		GameRegistry.registerBlock(blockEndPoint, blockEndPoint.getUnlocalizedName());
		GameRegistry.registerBlock(blockGui, blockGui.getUnlocalizedName());
		
		//register items
		
		//register tile entities
		GameRegistry.registerTileEntity(TileEntityCable.class, "cable");
		GameRegistry.registerTileEntity(TileEntityMasterNode.class, "masterNode");
		GameRegistry.registerTileEntity(TileEntityTestInventory.class, "testinventory");
		GameRegistry.registerTileEntity(TileEntityEndPoint.class, "tileEntityEndPoint");
		GameRegistry.registerTileEntity(TileEntityTestGui.class, "testGui");

		
	}
	
	@EventHandler
	public void load(FMLInitializationEvent e){
		
		//register gui handlers
		NetworkRegistry.instance().registerGuiHandler(instance, GuiHandlerBesu.getInstance());
		BLogger.debug(proxy);
		proxy.registerRenderers();
		proxy.registerGuis(GuiHandlerBesu.getInstance());
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent e){
	}
	
	private void instantiateBlocks(){
		blockCable = new BlockCable(NCConfig.block_cable);
		blockMasterNode = new BlockMasterNode(NCConfig.block_masterNode);
		blockEndPoint = new BlockEndPoint(NCConfig.block_endPoint);
		
		blockInventory = new BlockTestInventory(NCConfig.block_inventory);
		blockGui = new BlockTestGui(NCConfig.block_gui);
	}
}
