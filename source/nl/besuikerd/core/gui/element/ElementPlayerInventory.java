package nl.besuikerd.core.gui.element;

import java.util.List;

import net.minecraft.inventory.Slot;
import nl.besuikerd.core.gui.layout.LayoutDimension;
import nl.besuikerd.core.gui.layout.VerticalLayout;
import nl.besuikerd.core.inventory.SlotBesu;

public class ElementPlayerInventory extends ElementNamedContainer{
	
	public ElementPlayerInventory(List<? extends Slot> slots, int x, int y) {
		super(x, y, 0, 0, "Inventory");
		this.layout = new VerticalLayout();
		this.heightDimension = LayoutDimension.WRAP_CONTENT;
		this.widthDimension = LayoutDimension.WRAP_CONTENT;
		add(new ElementItemContainerArray(9, slots.subList(9, 36)).paddingBottom(5));
		add(new ElementItemContainerArray(9, slots.subList(0, 9)));
	}

	public ElementPlayerInventory(List<? extends Slot> slots) {
		this(slots, 0, 0);
	}
}
