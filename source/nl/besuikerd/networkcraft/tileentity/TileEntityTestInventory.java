package nl.besuikerd.networkcraft.tileentity;

import nl.besuikerd.core.BLogger;
import nl.besuikerd.core.BlockSide;
import nl.besuikerd.core.gui.GuiBase;
import nl.besuikerd.core.gui.element.ElementItemContainerArray;
import nl.besuikerd.core.gui.element.ElementLabel;
import nl.besuikerd.core.gui.element.ElementPlayerInventory;
import nl.besuikerd.core.gui.element.ElementStyledContainer;
import nl.besuikerd.core.gui.layout.LayoutDimension;
import nl.besuikerd.core.gui.layout.VerticalLayout;
import nl.besuikerd.core.inventory.ContainerBesu;
import nl.besuikerd.core.inventory.InventoryStackBesu;
import nl.besuikerd.core.inventory.TileEntityInventory;
import nl.besuikerd.core.inventory.InventoryStackBesu.StackBuilder;

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
			root.padding(10)
			.add(new ElementLabel("Super TestInventory!"))
			.add(new ElementItemContainerArray(9, inventorySlots.inventorySlots.subList(36, inventorySlots.inventorySlots.size())).paddingBottom(5))
			.add(new ElementPlayerInventory(inventorySlots.inventorySlots))
			;
		}
	}
}