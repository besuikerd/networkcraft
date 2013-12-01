package nl.besuikerd.networkcraft.gui.element;

import nl.besuikerd.networkcraft.core.utils.Tuple;

public class ElementItemContainer extends Element{

	protected Tuple texContainer = new Tuple(46, 0, 18, 18);
	protected int colorHovering = 0xffc5c5c5;
	
	public ElementItemContainer(int x, int y) {
		super(x, y, 18, 18);
	}
	
	public ElementItemContainer(){
		this(0, 0);
	}
	
	@Override
	public void draw(ElementContainer parent, int mouseX, int mouseY) {
		super.draw(parent, mouseX, mouseY);
		drawTexturedModalRect(0, 0, texContainer.int1(), texContainer.int2(), texContainer.int3(), texContainer.int4());
		
		if(isHovering()){
			drawRectangle(1, 1, width - 2, height - 2, colorHovering);
		}
	}
}
