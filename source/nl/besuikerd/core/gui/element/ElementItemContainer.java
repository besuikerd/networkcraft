package nl.besuikerd.core.gui.element;

import net.minecraft.inventory.Slot;
import nl.besuikerd.core.BLogger;
import nl.besuikerd.core.utils.Tuple;

public class ElementItemContainer extends Element{

	protected Slot slot;
	
	protected Tuple texContainer = new Tuple(46, 0, 18, 18);
	
	public ElementItemContainer(Slot slot, int x, int y) {
		super(x, y, 18, 18);
		this.slot = slot;
	}
	
	public ElementItemContainer(Slot slot){
		this(slot, 0, 0);
	}
	
	@Override
	public void draw(ElementContainer parent, int mouseX, int mouseY, ElementContainer root) {
		
		//re-align the slot
		slot.xDisplayPosition = absX() + 1 - root.absX();
		slot.yDisplayPosition = absY() + 1 - root.absY();
		
		super.draw(parent, mouseX, mouseY, root);
		drawTexturedModalRect(0, 0, texContainer.int1(), texContainer.int2(), texContainer.int3(), texContainer.int4());
	}
}
