package nl.besuikerd.networkcraft.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import nl.besuikerd.networkcraft.core.utils.ReflectUtils;
import cpw.mods.fml.common.network.IGuiHandler;

public class NCGuiHandler implements IGuiHandler{
	public static final int HANDLER_LIMIT = 256;
	
	private static NCGuiHandler instance = null;
	
	
	private INCGui[] registryClient;
	private Class<? extends NCContainer>[] registryServer;
	
	private NCGuiHandler(){
		this.registryClient = new INCGui[HANDLER_LIMIT];
		this.registryServer = new Class[HANDLER_LIMIT];
	}
	
	public static NCGuiHandler getInstance(){
		if(instance == null){
			instance = new NCGuiHandler();
		}
		return instance;
	}
	
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity entity = null;
		
		//id should be valid and a TileEntity must exist in the given coords
		if(id >= 0 && id < registryServer.length && world.blockExists(x, y, z) && (entity = world.getBlockTileEntity(x, y, z)) != null){
			Class<? extends NCContainer> clsContainer = this.registryServer[id];
			if(clsContainer != null){
				NCContainer container = ReflectUtils.newInstance(NCContainer.class);
				container.tileEntity = entity;
				return entity;
			}
		}
		return null;
	}
	
	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity entity = null;
		
		//id should be valid and a TileEntity must exist in the given coords
		if(id >= 0 && id < registryClient.length && world.blockExists(x, y, z) && (entity = world.getBlockTileEntity(x, y, z)) != null){
			INCGui registry = this.registryClient[id];
			
			
			if(registry != null){
				return registry.onOpenend(entity, player, world, x, y, z);
			}
		}
		return null;
	}
	
	public static void registerGui(int id, INCGui gui, Class<? extends NCContainer> containerClass){
		instance.registryClient[id] = gui;
		instance.registryServer[id] = containerClass;
	}
	
	public static void registerGui(int id, INCGui gui){
		registerGui(id, gui, null);
	}
	
	public static void registerGui(INumbered number, INCGui gui, Class<? extends NCContainer> containerClass){
		registerGui(number.getNumber(), gui, containerClass);
	}
	
	public static void registerGui(INumbered number, INCGui gui){
		registerGui(number, gui, null);
	}
	
}
