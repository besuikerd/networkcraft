package nl.besuikerd.networkcraft;

import net.minecraft.block.Block;
import net.minecraftforge.common.Configuration;
import nl.besuikerd.core.BLogger;
import nl.besuikerd.core.gui.GuiBase;
import nl.besuikerd.core.gui.GuiHandlerBesu;
import nl.besuikerd.core.gui.GuiId;
import nl.besuikerd.core.inventory.ContainerBesuWithPlayerInventory;
import nl.besuikerd.networkcraft.block.BlockCable;
import nl.besuikerd.networkcraft.block.BlockTestInventory;
import nl.besuikerd.networkcraft.graph.BlockMasterNode;
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
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid="networkcraft", name="NetworkCraft", version="0.0.0")
@NetworkMod(clientSideRequired=true)
public class NetworkCraft{
	
	public static boolean DEBUG_MODE = true;
	
	public static Block blockCable;
	public static Block blockMasterNode;
	public static Block blockInventory;
	
	
	@Instance(value="networkcraft")
	public static NetworkCraft instance;
	
	@SidedProxy(clientSide="nl.besuikerd.networkcraft.ClientProxy", serverSide="nl.besuikerd.networkcraft.CommonProxy")
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
		
		//register items
		
		//register tile entities
		GameRegistry.registerTileEntity(TileEntityCable.class, "cable");
		GameRegistry.registerTileEntity(TileEntityMasterNode.class, "masterNode");
		GameRegistry.registerTileEntity(TileEntityTestInventory.class, "testinventory");
		
		//register gui handlers
		NetworkRegistry.instance().registerGuiHandler(this, GuiHandlerBesu.getInstance());
		
		//register guis
		GuiHandlerBesu guiHandler = GuiHandlerBesu.getInstance();
		guiHandler.registerGui(GuiId.TEST, GuiBase.class);
		guiHandler.registerGui(GuiId.INVENTORYTEST, TileEntityTestInventory.GuiTileEntityTestInventory.class, ContainerBesuWithPlayerInventory.class);
	}
	
	@EventHandler
	public void load(FMLInitializationEvent e){
		proxy.registerRenderers();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent e){
	}
	
	private void instantiateBlocks(){
		blockCable = new BlockCable(NCConfig.block_cable);
		blockMasterNode = new BlockMasterNode(NCConfig.block_masterNode);
		blockInventory = new BlockTestInventory(NCConfig.block_inventory);
	}
}
