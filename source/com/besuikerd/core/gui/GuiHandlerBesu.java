package com.besuikerd.core.gui;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.besuikerd.core.BLogger;
import com.besuikerd.core.inventory.ContainerBesu;
import com.besuikerd.core.inventory.TileEntityInventory;
import com.besuikerd.core.utils.INumbered;
import com.besuikerd.core.utils.ReflectUtils;

import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandlerBesu implements IGuiHandler{
	
	private static GuiHandlerBesu instance = null;
	
	private Map<Integer, IGuiEntry> entries;
	private Map<String, Integer> nameMapping;
	
	private int counter;
	
	public GuiHandlerBesu(){
		this.entries = new HashMap<Integer, IGuiEntry>();
		this.nameMapping = new HashMap<String, Integer>();
	}
	
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world,
			int x, int y, int z) {
		Container c = null;
		IGuiEntry entry = entries.get(id);
		
		if(entry != null){
			if(entry.getContainerClass() != null){
				c = ReflectUtils.newInstance(entry.getContainerClass());
			}
			if(entry.getBinder() != null){
				entry.getBinder().bind(null, c, player, world, x, y, z);
			}
		}
		return c;
	}
	
	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world,
			int x, int y, int z) {
		IGuiEntry entry = entries.get(id);
		GuiBase g = null;
		Container c = null;
		if(entry != null && entry.getGuiClass() != null){
			if(entry.getContainerClass() != null){
				c = ReflectUtils.newInstance(entry.getContainerClass());
			}
			g = ReflectUtils.newInstance(entry.getGuiClass());
			
			if(entry.getBinder() != null){
				entry.getBinder().bind(g, c, player, world, x, y, z);
			}
		}
		BLogger.debug(g);
		return g == null ? null : g instanceof GuiBaseInventory ? new GuiContainerBesu(c, (GuiBaseInventory)g) : new GuiScreenBesu(g);
	}
	
	public int fromName(String name){
		Integer i = nameMapping.get(name);
		return i == null ? -1 : i;
	}
	
	public int fromEntry(IGuiEntry entry){
		return fromName(entry.getName());
	}
	
	public int addGui(IGuiEntry entry){
		nameMapping.put(entry.getName(), counter);
		entries.put(counter, entry);
		return counter++;
	}
	
	public void addGuis(IGuiEntry... entries){
		for(IGuiEntry entry : entries){
			addGui(entry);
		}
	}
}
