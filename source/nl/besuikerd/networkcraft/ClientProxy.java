package nl.besuikerd.networkcraft;

import nl.besuikerd.core.gui.GuiHandlerBesu;
import nl.besuikerd.core.gui.GuiId;
import nl.besuikerd.core.inventory.ContainerBesuWithPlayerInventory;
import nl.besuikerd.networkcraft.render.TileEntitySpecialRendererBlockCable;
import nl.besuikerd.networkcraft.tileentity.TileEntityCable;
import nl.besuikerd.networkcraft.tileentity.TileEntityTestGui;
import nl.besuikerd.networkcraft.tileentity.TileEntityTestInventory;
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
