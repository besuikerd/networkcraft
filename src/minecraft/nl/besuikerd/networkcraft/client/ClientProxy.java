package nl.besuikerd.networkcraft.client;

import nl.besuikerd.core.BLogger;
import nl.besuikerd.networkcraft.CommonProxy;
import nl.besuikerd.networkcraft.TileEntityBlockCable;
import nl.besuikerd.networkcraft.TileEntitySpecialRendererBlockCable;
import cpw.mods.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy{
	@Override
	public void registerRenderers() {
		super.registerRenderers();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBlockCable.class, new TileEntitySpecialRendererBlockCable());
	}
}
