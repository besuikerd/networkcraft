package nl.besuikerd.core.inventory;

import nl.besuikerd.core.BLogger;
import nl.besuikerd.core.BlockSide;
import nl.besuikerd.gui.GuiBase;
import nl.besuikerd.gui.element.ElementItemContainerArray;
import nl.besuikerd.gui.element.ElementPlayerInventory;
import nl.besuikerd.gui.element.ElementStyledContainer;
import nl.besuikerd.gui.layout.LayoutDimension;
import nl.besuikerd.gui.layout.VerticalLayout;

public class TileEntityTestInventory extends TileEntityInventory {
	public TileEntityTestInventory() {
		inventory.addStacks(27, new InventoryStackBesu.StackBuilder());
	}
	
	
	public static class GuiTileEntityTestInventory extends GuiBase{
		public GuiTileEntityTestInventory(ContainerBesu container) {
			super(container);
		}

		@Override
		public void init() {
			root.add(new ElementItemContainerArray(9, inventorySlots.inventorySlots.subList(36, inventorySlots.inventorySlots.size())));
			root.add(new ElementPlayerInventory(inventorySlots.inventorySlots));
		}
	}
}
