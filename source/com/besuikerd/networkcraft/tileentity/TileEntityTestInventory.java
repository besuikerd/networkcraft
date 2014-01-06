package com.besuikerd.networkcraft.tileentity;

import com.besuikerd.core.gui.GuiBase;
import com.besuikerd.core.gui.element.ElementButton;
import com.besuikerd.core.gui.element.ElementContainer;
import com.besuikerd.core.gui.element.ElementInputField;
import com.besuikerd.core.gui.element.ElementItemContainerArray;
import com.besuikerd.core.gui.element.ElementLabel;
import com.besuikerd.core.gui.element.ElementPlayerInventory;
import com.besuikerd.core.gui.element.ElementProgressBar;
import com.besuikerd.core.gui.element.ElementScrollContainer;
import com.besuikerd.core.gui.layout.Alignment;
import com.besuikerd.core.gui.layout.HorizontalLayout;
import com.besuikerd.core.gui.layout.VerticalLayout;
import com.besuikerd.core.inventory.ContainerBesu;
import com.besuikerd.core.inventory.InventoryStackBesu;
import com.besuikerd.core.inventory.TileEntityInventory;

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
				.layout(new HorizontalLayout(1,0))
				.add(new ElementContainer()
					.layout(new VerticalLayout())
					.add(new ElementContainer()
						.layout(new HorizontalLayout())
						.add(new ElementInputField(60))
						.add(new ElementInputField(60))
						.add(new ElementInputField(60))
					)
					.add(new ElementLabel("Super TestInventory!").align(Alignment.CENTER))
					.add(new ElementItemContainerArray(9, inventorySlots.inventorySlots.subList(36, inventorySlots.inventorySlots.size())).paddingBottom(5))
					.add(new ElementPlayerInventory(inventorySlots.inventorySlots))
				)
				.add(new ElementScrollContainer(50, new ElementContainer()
					.layout(new HorizontalLayout())
					.add(new ElementScrollContainer(300, new ElementContainer()
						.layout(new HorizontalLayout())
						.add(new ElementButton(50, 750, "bla"))
					))
				))
			;
		}
	}
}
