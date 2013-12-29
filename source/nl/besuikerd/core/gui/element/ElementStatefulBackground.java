package nl.besuikerd.core.gui.element;

import nl.besuikerd.core.gui.texture.ElementState;
import nl.besuikerd.core.gui.texture.IStateFulBackground;
import nl.besuikerd.core.gui.texture.StateFulBackground;

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
	public void draw(ElementContainer parent, int mouseX, int mouseY, ElementContainer root) {
		super.draw(parent, mouseX, mouseY, root);
		drawStatefulBackgroundFromTextures(statefulBackground);
	}
}
