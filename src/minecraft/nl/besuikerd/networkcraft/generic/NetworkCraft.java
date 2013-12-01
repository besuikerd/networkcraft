package nl.besuikerd.networkcraft.generic;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import nl.besuikerd.networkcraft.core.NCConfig;
import nl.besuikerd.networkcraft.core.NCLogger;
import nl.besuikerd.networkcraft.gui.NCGuiHandler;
import nl.besuikerd.networkcraft.gui.NCGuiId;
import nl.besuikerd.networkcraft.gui.element.GuiBase;
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
	
	@Instance(value="networkcraft")
	public static NetworkCraft instance;
	
	@SidedProxy(clientSide="nl.besuikerd.networkcraft.generic.client.ClientProxy", serverSide="nl.besuikerd.networkcraft.generic.CommonProxy")
	public static CommonProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e){
		//initialize logger
		NCLogger.init();
		
		//setup configuration
		Configuration cfg = new Configuration(e.getSuggestedConfigurationFile());
		NCConfig.init(cfg);
		cfg.save();
		
		instantiateBlocks();
		
		//register blocks
		GameRegistry.registerBlock(blockRouter, blockRouter.getUnlocalizedName());
		GameRegistry.registerBlock(cable, cable.getUnlocalizedName());
		
		
		GameRegistry.registerBlock(blockGui, blockGui.getUnlocalizedName());
		
		//register items
		
		//register tile entities
		GameRegistry.registerTileEntity(TileEntityBlockCable.class, "cable");
		GameRegistry.registerTileEntity(TileEntityConnecting.class, "connecting");
		
		//register gui handlers
		NetworkRegistry.instance().registerGuiHandler(this, NCGuiHandler.getInstance());
		
		//register guis
		NCGuiHandler guiHandler = NCGuiHandler.getInstance();
		guiHandler.registerGui(NCGuiId.TEST, new GuiBase());
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
	}
}
