package nl.besuikerd.gui.element;

import java.util.Collection;

import net.minecraft.inventory.Slot;
import nl.besuikerd.gui.layout.HoritzontalLayout;


public class ElementItemContainerArray extends ElementContainer{
	
	
	public ElementItemContainerArray(int x, int y, int columns, Collection<? extends Slot> slots) {
		super(x, y, columns * 18, (int) (Math.ceil(slots.size() / columns) * 18));
		this.layout = new HoritzontalLayout();
		for(Slot slot : slots){
			add(new ElementItemContainer(slot));
		}
	}
	
	public ElementItemContainerArray(int columns, Collection<? extends Slot> slots) {
		this(0, 0, columns, slots);
	}
}
