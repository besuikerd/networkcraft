package nl.besuikerd.networkcraft;

import nl.besuikerd.core.BLogger;
import nl.besuikerd.core.gui.GuiHandlerBesu;
import nl.besuikerd.core.gui.GuiId;
import nl.besuikerd.core.inventory.ContainerBesuWithPlayerInventory;

public class CommonProxy {
	public void registerRenderers(){
		 
	}
	
	public void registerGuis(GuiHandlerBesu handler){
		BLogger.debug("opening server gui");
		handler.registerGuiForServer(GuiId.INVENTORYTEST, ContainerBesuWithPlayerInventory.class);
	}
}
