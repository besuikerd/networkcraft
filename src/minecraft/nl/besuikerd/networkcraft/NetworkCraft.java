package nl.besuikerd.networkcraft;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import nl.besuikerd.core.BLogger;
import nl.besuikerd.core.inventory.ContainerBesu;
import nl.besuikerd.core.inventory.ContainerBesuWithPlayerInventory;
import nl.besuikerd.core.inventory.TileEntityTestInventory;
import nl.besuikerd.gui.GuiBase;
import nl.besuikerd.gui.GuiHandlerBesu;
import nl.besuikerd.gui.GuiId;
import nl.besuikerd.networkcraft.block.BlockCable;
import nl.besuikerd.networkcraft.block.BlockGui;
import nl.besuikerd.networkcraft.block.BlockRouter;
import nl.besuikerd.networkcraft.block.BlockTestInventory;
import nl.besuikerd.networkcraft.tileentity.TileEntityCable;
import nl.besuikerd.networkcraft.tileentity.TileEntityConnecting;
import cpw.mods.fml.client.registry.ClientRegistry;
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
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid="networkcraft", name="NetworkCraft", version="0.0.0")
@NetworkMod(clientSideRequired=true)
public class NetworkCraft{
	
	public static boolean DEBUG_MODE = true;
	
	public static Block blockRouter;
	public static Block cable;
	
	public static Block blockGui;
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
		GameRegistry.registerBlock(blockRouter, blockRouter.getUnlocalizedName());
		GameRegistry.registerBlock(cable, cable.getUnlocalizedName());
		
		
		GameRegistry.registerBlock(blockGui, blockGui.getUnlocalizedName());
		GameRegistry.registerBlock(blockInventory, blockInventory.getUnlocalizedName());
		
		//register items
		
		//register tile entities
		GameRegistry.registerTileEntity(TileEntityCable.class, "cable");
		GameRegistry.registerTileEntity(TileEntityConnecting.class, "connecting");
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
		blockRouter = new BlockRouter(NCConfig.block_router);
		cable = new BlockCable(NCConfig.block_cable);
		blockGui = new BlockGui(450);
		blockInventory = new BlockTestInventory(451);
	}
}
