package nl.besuikerd.networkcraft.gui.element;

import java.util.List;

import net.minecraft.inventory.Slot;
import nl.besuikerd.networkcraft.core.inventory.NCSlot;
import nl.besuikerd.networkcraft.gui.layout.VerticalLayout;

public class ElementPlayerInventory extends ElementNamedContainer{
	
	public ElementPlayerInventory(List<? extends Slot> slots, int x, int y, int width, int height) {
		super(x, y, width, height, "Inventory");
		this.layout = new VerticalLayout(0, 5);
		add(new ElementItemContainerArray(9, slots.subList(9, 36)));
		add(new ElementItemContainerArray(9, slots.subList(0, 9)));
	}

	public ElementPlayerInventory(List<? extends NCSlot> slots, int width, int height) {
		this(slots, 0, 0, width, height);
	}
}
