package nl.besuikerd.core.inventory;

import nl.besuikerd.gui.GuiBase;
import nl.besuikerd.gui.element.ElementItemContainerArray;
import nl.besuikerd.gui.element.ElementPlayerInventory;
import nl.besuikerd.gui.element.ElementStyledContainer;
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
			root = new ElementStyledContainer(0, 0, 300, 300);
			root.setLayout(new VerticalLayout(0, 5));
			root.add(new ElementItemContainerArray(9, inventorySlots.inventorySlots.subList(36, inventorySlots.inventorySlots.size())));
			root.add(new ElementPlayerInventory(inventorySlots.inventorySlots, 10, 10, 250, 100));
		}
	}
}
