package nl.besuikerd.inetcraft.generic;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.common.MinecraftForge;
import nl.besuikerd.inetcraft.core.INCLogger;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid="inetcraft", name="INetCraft", version="0.0.0")
@NetworkMod(clientSideRequired=true)
public class NetworkCraft{
	
	public static boolean DEBUG_MODE = true;
	
	public static final Block blockRouter = new BlockRouter(1024);
	public static final Block cable = new Cable(999, Material.rock);
	
	@Instance(value="networkcraft")
	public static NetworkCraft instance;
	
	@SidedProxy(clientSide="nl.besuikerd.networkcraft.generic.client.ClientProxy", serverSide="nl.besuikerd.networkcraft.generic.CommonProxy")
	public static CommonProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e){
		//initialize logger
		INCLogger.init();
		
		//register blocks
		GameRegistry.registerBlock(cable, "cable");
		GameRegistry.registerBlock(blockRouter, blockRouter.getUnlocalizedName());
		
		//register items
		
		//register tile entities
		GameRegistry.registerTileEntity(CableTileEntity.class, "cable");
	}
	
	@EventHandler
	public void load(FMLInitializationEvent e){
		proxy.registerRenderers();
		
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent e){
		
		
	}
}
