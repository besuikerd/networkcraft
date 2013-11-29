package nl.besuikerd.networkcraft.gui;

public class ElementLabel extends Element{

	protected String text;
	
	public ElementLabel(int x, int y, int width, int height, String text) {
		super(x, y, width, height);
		this.text = text;
	}

	@Override
	public void draw(Box parent, int mouseX, int mouseY) {
		// TODO Auto-generated method stub
		
	}
}
