package com.besuikerd.networkcraft;

import com.besuikerd.networkcraft.block.BlockScreen;
import com.besuikerd.networkcraft.block.Blocks;
import com.besuikerd.networkcraft.tileentity.TileEntityScreen;

import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy {
	public static final String CLS = "com.besuikerd.networkcraft.CommonProxy";

	public void registerBlocks() {
		GameRegistry.registerBlock(Blocks.SCREEN, "besu.block.Screen");
	}

	public void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntityScreen.class, "besu.tile.Screen");
	}

	public void registerRenderers() {

	}
}
