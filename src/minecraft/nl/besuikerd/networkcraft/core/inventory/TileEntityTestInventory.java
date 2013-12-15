package nl.besuikerd.networkcraft.core.inventory;

import nl.besuikerd.networkcraft.gui.GuiBase;
import nl.besuikerd.networkcraft.gui.element.ElementPlayerInventory;
import nl.besuikerd.networkcraft.gui.element.ElementStyledContainer;
import nl.besuikerd.networkcraft.gui.layout.VerticalLayout;

public class TileEntityTestInventory extends TileEntityInventory {
	public TileEntityTestInventory() {
	}
	
	
	public static class GuiTileEntityTestInventory extends GuiBase{
		public GuiTileEntityTestInventory(NCContainer container) {
			super(container);
		}

		@Override
		public void init() {
			root = new ElementStyledContainer(0, 0, 300, 300);
			root.setLayout(new VerticalLayout(0, 5));
			root.add(new ElementPlayerInventory(((NCContainer)inventorySlots).getPlayerSlots(), 10, 10, 250, 100));
		}
	}
}
