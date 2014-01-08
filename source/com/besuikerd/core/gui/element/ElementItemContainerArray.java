package com.besuikerd.core.gui.element;

import java.util.Collection;

import com.besuikerd.core.gui.layout.HorizontalLayout;
import com.besuikerd.core.gui.layout.LayoutDimension;

import net.minecraft.inventory.Slot;

public class ElementItemContainerArray extends ElementContainer{
	
	protected int columns;
	
	public ElementItemContainerArray(int x, int y, int columns, Collection<? extends Slot> slots) {
		super(x, y, 0, (int) (Math.ceil(slots.size() / columns) * 18));
		this.columns = columns;
		this.layout = new HorizontalLayout();
		this.heightDimension = LayoutDimension.WRAP_CONTENT;
		for(Slot slot : slots){
			add(new ElementItemContainer(slot));
		}
	}
	
	@Override
	public void dimension() {
		super.dimension();
		this.width = columns * 18 + paddingLeft + paddingRight;
	}
	
	public ElementItemContainerArray(int columns, Collection<? extends Slot> slots) {
		this(0, 0, columns, slots);
	}
}
