package com.besuikerd.networkcraft;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.Configuration;

import com.besuikerd.core.BLogger;
import com.besuikerd.core.gui.GuiHandlerBesu;
import com.besuikerd.core.packet.PacketBesu;
import com.besuikerd.core.packet.PacketHandlerBesu;
import com.besuikerd.networkcraft.block.BlockCable;
import com.besuikerd.networkcraft.block.BlockEndPoint;
import com.besuikerd.networkcraft.block.BlockMasterNode;
import com.besuikerd.networkcraft.dv.BlockDestination;
import com.besuikerd.networkcraft.dv.BlockNode;
import com.besuikerd.networkcraft.dv.TileEntityDestination;
import com.besuikerd.networkcraft.dv.TileEntityNode;
import com.besuikerd.networkcraft.graph.TileEntityEndPoint;
import com.besuikerd.networkcraft.graph.TileEntityMasterNode;
import com.besuikerd.networkcraft.tileentity.TileEntityCable;

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
	
	public static Block blockNode;
	public static Block blockDestination;
	
	public static final GuiHandlerBesu GUI_HANDLER = new GuiHandlerBesu();
	
	@Instance()
	public static NetworkCraft instance;
	
	@SidedProxy(clientSide=Reference.PROXY_CLIENT, serverSide=Reference.PROXY_SERVER)
	public static CommonProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e){
		//initialize logger
		BLogger.init(true);
		
		//setup configuration
		Configuration cfg = new Configuration(e.getSuggestedConfigurationFile());
		NCConfig.init(cfg);
		cfg.save();
		
		instantiateBlocks();
		
		//register blocks
		GameRegistry.registerBlock(blockCable, blockCable.getUnlocalizedName());
		GameRegistry.registerBlock(blockMasterNode, blockMasterNode.getUnlocalizedName());
		GameRegistry.registerBlock(blockEndPoint, blockEndPoint.getUnlocalizedName());

		GameRegistry.registerBlock(blockNode, "blocknode");
		GameRegistry.registerBlock(blockDestination, "blockdestination");
		
		
		//register items
		
		//register tile entities
		GameRegistry.registerTileEntity(TileEntityCable.class, "cable");
		GameRegistry.registerTileEntity(TileEntityMasterNode.class, "masterNode");
		GameRegistry.registerTileEntity(TileEntityEndPoint.class, "tileEntityEndPoint");
		
		GameRegistry.registerTileEntity(TileEntityNode.class, "teNode");
		GameRegistry.registerTileEntity(TileEntityDestination.class, "toDestination");
	}
	
	@EventHandler
	public void load(FMLInitializationEvent e){
		
		//register gui handlers
		NetworkRegistry.instance().registerGuiHandler(instance, GUI_HANDLER);
		proxy.registerRenderers();
		proxy.registerGuis(GUI_HANDLER);
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent e){
	}
	
	private void instantiateBlocks(){
		blockCable = new BlockCable(NCConfig.block_cable);
		blockMasterNode = new BlockMasterNode(NCConfig.block_masterNode);
		blockEndPoint = new BlockEndPoint(NCConfig.block_endPoint);
		
		blockNode = new BlockNode(600, Material.grass);
		blockDestination = new BlockDestination(601, Material.grass);
	}
}
