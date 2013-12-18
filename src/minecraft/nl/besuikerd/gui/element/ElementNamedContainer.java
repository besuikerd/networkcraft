package nl.besuikerd.gui.element;

public class ElementNamedContainer extends ElementContainer{

	protected String name;
	
	public ElementNamedContainer(int width, int height, String name) {
		this(0, 0, width, height, name);
	}
	
	

	public ElementNamedContainer(int x, int y, int width, int height, String name) {
		super(x, y, width, height);
		this.name = name;
		add(new ElementLabel(name));
	}


	@Override
	public void draw(ElementContainer parent, int mouseX, int mouseY) {
		super.draw(parent, mouseX, mouseY);
	}
}
