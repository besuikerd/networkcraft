package nl.besuikerd.networkcraft;

import net.minecraft.block.Block;
import net.minecraftforge.common.Configuration;
import nl.besuikerd.core.BLogger;
import nl.besuikerd.core.gui.GuiBase;
import nl.besuikerd.core.gui.GuiHandlerBesu;
import nl.besuikerd.core.gui.GuiId;
import nl.besuikerd.core.inventory.ContainerBesuWithPlayerInventory;
import nl.besuikerd.core.packet.PacketBesu;
import nl.besuikerd.core.packet.PacketHandlerBesu;
import nl.besuikerd.networkcraft.block.BlockCable;
import nl.besuikerd.networkcraft.block.BlockEndPoint;
import nl.besuikerd.networkcraft.block.BlockMasterNode;
import nl.besuikerd.networkcraft.block.BlockTestInventory;
import nl.besuikerd.networkcraft.graph.TileEntityEndPoint;
import nl.besuikerd.networkcraft.graph.TileEntityMasterNode;
import nl.besuikerd.networkcraft.tileentity.TileEntityCable;
import nl.besuikerd.networkcraft.tileentity.TileEntityTestInventory;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.NetworkMod.SidedPacketHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;

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
		
		//register items
		
		//register tile entities
		GameRegistry.registerTileEntity(TileEntityCable.class, "cable");
		GameRegistry.registerTileEntity(TileEntityMasterNode.class, "masterNode");
		GameRegistry.registerTileEntity(TileEntityTestInventory.class, "testinventory");
		GameRegistry.registerTileEntity(TileEntityEndPoint.class, "tileEntityEndPoint");
		

		
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
		
	}
}
