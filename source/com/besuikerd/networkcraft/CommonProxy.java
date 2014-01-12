package com.besuikerd.networkcraft;

import static com.besuikerd.networkcraft.GuiEntry.TESTGUI;
import static com.besuikerd.networkcraft.GuiEntry.TESTINVENTORY;

import com.besuikerd.core.BLogger;
import com.besuikerd.core.gui.GuiHandlerBesu;

public class CommonProxy {
	public void registerRenderers(){
		 
	}
	
	public void registerGuis(GuiHandlerBesu handler){
		BLogger.debug("registered guis!!");
		handler.addGuis(
			TESTINVENTORY,
			TESTGUI	
		);
	}
}
