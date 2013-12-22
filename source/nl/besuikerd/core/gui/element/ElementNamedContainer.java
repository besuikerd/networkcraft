package nl.besuikerd.core.gui.element;

import nl.besuikerd.core.gui.layout.LayoutDimension;

public class ElementNamedContainer extends ElementContainer{

	protected String name;
	
	public ElementNamedContainer(int width, int height, String name) {
		this(0, 0, width, height, name);
	}
	
	

	public ElementNamedContainer(int x, int y, int width, int height, String name) {
		super(x, y, width, height);
		this.name = name;
		ElementLabel label = new ElementLabel(name);
//		label.widthDimension = LayoutDimension.MATCH_PARENT;
		add(label);
	}


	@Override
	public void draw(ElementContainer parent, int mouseX, int mouseY, ElementContainer root) {
		super.draw(parent, mouseX, mouseY, root);
	}
}
