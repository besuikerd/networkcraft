package com.besuikerd.core.gui.element;

import java.util.Collection;

import com.besuikerd.core.gui.layout.HorizontalLayout;
import com.besuikerd.core.gui.layout.LayoutDimension;

import net.minecraft.inventory.Slot;

public class ElementItemContainerArray extends ElementContainer{
	
	
	public ElementItemContainerArray(int x, int y, int columns, Collection<? extends Slot> slots) {
		super(x, y, columns * 18, (int) (Math.ceil(slots.size() / columns) * 18));
		this.layout = new HorizontalLayout();
		this.heightDimension = LayoutDimension.WRAP_CONTENT;
		for(Slot slot : slots){
			add(new ElementItemContainer(slot));
		}
	}
	
	public ElementItemContainerArray(int columns, Collection<? extends Slot> slots) {
		this(0, 0, columns, slots);
	}
}