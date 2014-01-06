package com.besuikerd.networkcraft;

import com.besuikerd.core.BLogger;
import com.besuikerd.core.gui.GuiHandlerBesu;
import com.besuikerd.core.gui.GuiId;
import com.besuikerd.core.inventory.ContainerBesuWithPlayerInventory;

public class CommonProxy {
	public void registerRenderers(){
		 
	}
	
	public void registerGuis(GuiHandlerBesu handler){
		handler.registerGuiForServer(GuiId.INVENTORYTEST, ContainerBesuWithPlayerInventory.class);
		handler.registerGuiForServer(GuiId.GUITEST);
	}
}
