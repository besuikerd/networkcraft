package nl.besuikerd.core.gui.element;

import nl.besuikerd.core.gui.texture.TexturedBackground;

import org.lwjgl.opengl.GL11;

public class ElementProgressBar extends ElementStyledContainer{

	private double progress = 0;
	private int color = 0xFFFF0000;

	public ElementProgressBar(int width, int height) {
		super(width, height, TexturedBackground.CONTAINER);
	}
	
	public ElementProgressBar(int width, int height, int color){
		this(width, height);
		this.color = color;
	}

	@Override
	public void draw(ElementContainer parent, int mouseX, int mouseY, ElementContainer root) {
		super.draw(parent, mouseX, mouseY, root);
		drawRect(absX() + 2, absY() + 2, absX()+(int)((width-4)*progress), absY()+height-2, color);
				
	}
	
	public void addProgress(Double d){
		progress+=d;
	}
	
	public double getProgress() {
		return progress;
	}

	public void setProgress(double progress) {
		this.progress = progress;
	}

}
