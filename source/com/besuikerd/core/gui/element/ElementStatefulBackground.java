package com.besuikerd.core.gui.element;

import com.besuikerd.core.gui.texture.ElementState;
import com.besuikerd.core.gui.texture.IStateFulBackground;

public class ElementStatefulBackground extends Element{

	protected IStateFulBackground<ElementState> statefulBackground;
	
	public ElementStatefulBackground(IStateFulBackground<ElementState> statefulBackground, int x, int y, int width, int height) {
		super(x, y, width, height);
		this.statefulBackground = statefulBackground;
	}

	public ElementStatefulBackground(IStateFulBackground<ElementState> statefulBackground, int width, int height) {
		super(width, height);
		this.statefulBackground = statefulBackground;
	}
	
	@Override
	public void draw(ElementRootContainer root, int mouseX, int mouseY) {
		super.draw(root, mouseX, mouseY);
		drawStatefulBackgroundFromTextures(statefulBackground);
	}
	
	public ElementStatefulBackground background(IStateFulBackground<ElementState> background){
		this.statefulBackground = background;
		return this;
	}
}
