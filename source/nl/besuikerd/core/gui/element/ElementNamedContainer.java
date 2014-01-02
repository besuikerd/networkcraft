package nl.besuikerd.core.gui.element;

import nl.besuikerd.core.gui.layout.Alignment;
import nl.besuikerd.core.gui.layout.LayoutDimension;

public class ElementNamedContainer extends ElementContainer{

	protected String name;
	
	public ElementNamedContainer(int width, int height, String name) {
		this(0, 0, width, height, name);
	}
	
	

	public ElementNamedContainer(int x, int y, int width, int height, String name) {
		super(x, y, width, height);
		this.name = name;
		add(new ElementLabel(name).align(Alignment.CENTER));
	}


	@Override
	public void draw(ElementRootContainer root, int mouseX, int mouseY) {
		super.draw(root, mouseX, mouseY);
	}
}
