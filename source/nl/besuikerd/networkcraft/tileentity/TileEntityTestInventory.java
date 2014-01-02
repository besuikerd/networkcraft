package nl.besuikerd.networkcraft.tileentity;

import nl.besuikerd.core.gui.GuiBase;
import nl.besuikerd.core.gui.element.ElementButton;
import nl.besuikerd.core.gui.element.ElementContainer;
import nl.besuikerd.core.gui.element.ElementInputField;
import nl.besuikerd.core.gui.element.ElementItemContainerArray;
import nl.besuikerd.core.gui.element.ElementLabel;
import nl.besuikerd.core.gui.element.ElementPlayerInventory;
import nl.besuikerd.core.gui.element.ElementProgressBar;
import nl.besuikerd.core.gui.element.ElementScrollContainer;
import nl.besuikerd.core.gui.layout.Alignment;
import nl.besuikerd.core.gui.layout.HorizontalLayout;
import nl.besuikerd.core.gui.layout.VerticalLayout;
import nl.besuikerd.core.inventory.ContainerBesu;
import nl.besuikerd.core.inventory.InventoryStackBesu;
import nl.besuikerd.core.inventory.TileEntityInventory;

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
						.add(new ElementProgressBar(100, 10).setProgress(0.5))
					)
					.add(new ElementContainer()
						.layout(new HorizontalLayout())
						.add(new ElementInputField(60))
						.add(new ElementInputField(60))
						.add(new ElementInputField(60))
					)
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
//				.add(new ElementScrollBar(10))
				.add(new ElementScrollContainer(50, new ElementContainer()
//					.layout(new HorizontalLayout())
//					.add(new ElementScrollContainer(300, new ElementContainer()
						.layout(new HorizontalLayout())
						.add(new ElementButton(50, 750, "bla"))
//					))
				))
				
			
			;
		}
	}
}
