package nl.besuikerd.networkcraft.gui.element;

public class ElementLabel extends Element{

	protected String text;
	
	public ElementLabel(int x, int y, int width, int height, String text) {
		super(x, y, width, height);
		this.text = text;
	}

	@Override
	public void draw(ElementContainer parent, int mouseX, int mouseY) {
		super.draw(parent, mouseX, mouseY);
	}
}
