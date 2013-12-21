package nl.besuikerd.networkcraft;

import nl.besuikerd.core.BLogger;
import nl.besuikerd.networkcraft.tileentity.TileEntityCable;
import nl.besuikerd.networkcraft.tileentity.TileEntitySpecialRendererBlockCable;
import cpw.mods.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy{
	@Override
	public void registerRenderers() {
		super.registerRenderers();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCable.class, new TileEntitySpecialRendererBlockCable());
	}
}
