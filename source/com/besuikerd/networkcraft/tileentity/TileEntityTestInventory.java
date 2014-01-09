package com.besuikerd.networkcraft.tileentity;

import com.besuikerd.core.gui.GuiBase;
import com.besuikerd.core.gui.GuiTileEntity;
import com.besuikerd.core.gui.element.ElementButton;
import com.besuikerd.core.gui.element.ElementContainer;
import com.besuikerd.core.gui.element.ElementInputField;
import com.besuikerd.core.gui.element.ElementItemContainerArray;
import com.besuikerd.core.gui.element.ElementLabel;
import com.besuikerd.core.gui.element.ElementPlayerInventory;
import com.besuikerd.core.gui.element.ElementProgressBar;
import com.besuikerd.core.gui.element.ElementScrollContainer;
import com.besuikerd.core.gui.event.Trigger;
import com.besuikerd.core.gui.layout.Alignment;
import com.besuikerd.core.gui.layout.HorizontalLayout;
import com.besuikerd.core.gui.layout.LayoutDimension;
import com.besuikerd.core.gui.layout.VerticalLayout;
import com.besuikerd.core.inventory.ContainerBesu;
import com.besuikerd.core.inventory.InventoryStackBesu;
import com.besuikerd.core.inventory.TileEntityInventory;

public class TileEntityTestInventory extends TileEntityInventory {
	
	public TileEntityTestInventory() {
		inventory.addStacks(10, new InventoryStackBesu.StackBuilder());
	}
	
	public static class GuiTileEntityTestInventory extends GuiTileEntity{
		public GuiTileEntityTestInventory(ContainerBesu container) {
			super(container);
		}

		
		@Override
		public void init() {
			root.add(
				new ElementLabel("TileEntityTestInventory").align(Alignment.CENTER),
				new ElementContainer().layout(new HorizontalLayout()).add(
					new ElementItemContainerArray(3, inventorySlots.inventorySlots.subList(36, 45)).align(Alignment.CENTER),
					ElementProgressBar.progressBarArrow().max(1000).trigger(Trigger.UPDATE, "updateBurn").align(Alignment.CENTER).padding(8),
					new ElementItemContainerArray(1, inventorySlots.inventorySlots.subList(45, 46)).align(Alignment.CENTER)
				).align(Alignment.RIGHT),
				new ElementPlayerInventory(inventorySlots.inventorySlots)
			);
		}
		
		public void updateBurn(ElementProgressBar e){
			if(e.getProgress() == e.getMax()){
				e.reset();
			} else{
				e.increment();
			}
		}
	}
	
	
}
