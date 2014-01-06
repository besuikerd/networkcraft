package com.besuikerd.networkcraft;

import com.besuikerd.core.gui.GuiHandlerBesu;
import com.besuikerd.core.gui.GuiId;
import com.besuikerd.core.inventory.ContainerBesuWithPlayerInventory;
import com.besuikerd.networkcraft.render.TileEntitySpecialRendererBlockCable;
import com.besuikerd.networkcraft.tileentity.TileEntityCable;
import com.besuikerd.networkcraft.tileentity.TileEntityTestGui;
import com.besuikerd.networkcraft.tileentity.TileEntityTestInventory;

import cpw.mods.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy{
	@Override
	public void registerRenderers() {
		super.registerRenderers();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCable.class, new TileEntitySpecialRendererBlockCable());
	}
	
	@Override
	public void registerGuis(GuiHandlerBesu handler) {
		handler.registerGui(GuiId.INVENTORYTEST, TileEntityTestInventory.GuiTileEntityTestInventory.class, ContainerBesuWithPlayerInventory.class);
		handler.registerGui(GuiId.GUITEST, TileEntityTestGui.Gui.class);
	}
}
