package com.besuikerd.core.gui.element;

import com.besuikerd.core.gui.texture.scalable.ScalableTexture;

public class ElementProgressBar extends ElementStyledContainer{

	private double progress = 0;
	private int color = 0xFFFF0000;

	public ElementProgressBar(int width, int height) {
		super(width, height, ScalableTexture.CONTAINER);
	}
	
	public ElementProgressBar(int width, int height, int color){
		this(width, height);
		this.color = color;
	}

	@Override 
	public void draw(ElementRootContainer root, int mouseX, int mouseY) {
		super.draw(root, mouseX, mouseY);
		drawRect(absX() + 2, absY() + 2, absX()+(int)((width-4)*progress), absY()+height-2, color);
	}
	
	public ElementProgressBar addProgress(double d){
		progress+=d;
		return this;
	}
	
	public double getProgress() {
		return progress;
	}

	public ElementProgressBar setProgress(double progress) {
		this.progress = progress;
		return this;
	}

}
