package com.besuikerd.networkcraft;

import com.besuikerd.core.gui.GuiHandlerBesu;
import com.besuikerd.networkcraft.render.TileEntitySpecialRendererBlockCable;
import com.besuikerd.networkcraft.tileentity.TileEntityCable;

import cpw.mods.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy{
	@Override
	public void registerRenderers() {
		super.registerRenderers();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCable.class, new TileEntitySpecialRendererBlockCable());
	}
	
}
