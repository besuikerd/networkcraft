package nl.besuikerd.inetcraft.generic.client;

import cpw.mods.fml.client.registry.ClientRegistry;
import nl.besuikerd.inetcraft.generic.CableRenderer;
import nl.besuikerd.inetcraft.generic.CableTileEntity;
import nl.besuikerd.inetcraft.generic.CommonProxy;

public class ClientProxy extends CommonProxy{
	@Override
	public void registerRenderers() {
		super.registerRenderers();
		
		ClientRegistry.bindTileEntitySpecialRenderer(CableTileEntity.class, new CableRenderer());
		
	}
}
