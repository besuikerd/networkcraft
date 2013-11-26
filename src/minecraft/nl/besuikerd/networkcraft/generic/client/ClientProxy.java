package nl.besuikerd.networkcraft.generic.client;

import nl.besuikerd.networkcraft.core.NCLogger;
import nl.besuikerd.networkcraft.generic.CommonProxy;
import nl.besuikerd.networkcraft.generic.TileEntityBlockCable;
import nl.besuikerd.networkcraft.generic.TileEntitySpecialRendererBlockCable;
import cpw.mods.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy{
	@Override
	public void registerRenderers() {
		super.registerRenderers();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBlockCable.class, new TileEntitySpecialRendererBlockCable());
		
	}
}
