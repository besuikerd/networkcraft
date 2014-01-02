package nl.besuikerd.core.gui.element;

import nl.besuikerd.core.gui.layout.LayoutDimension;
import nl.besuikerd.core.gui.texture.ITexturedBackground;
import nl.besuikerd.core.gui.texture.TexturedBackground;

public class ElementStyledContainer extends ElementContainer{

	protected ITexturedBackground background;
	
	public ElementStyledContainer(int x, int y, int width, int height, ITexturedBackground background) {
		super(x, y, width, height);
		this.background = background;
		paddingTop = background.edgeTop().int4();
		paddingRight = background.edgeRight().int3();
		paddingBottom = background.edgeBottom().int4();
		paddingLeft = background.edgeLeft().int3();
	}
	
	public ElementStyledContainer(int width, int height, ITexturedBackground background) {
		this(0, 0, width, height, background);
	}

	public ElementStyledContainer(LayoutDimension width, LayoutDimension height, ITexturedBackground background) {
		this(0, 0, 0, 0, background);
		this.widthDimension = width;
		this.heightDimension = height;
	}
	
	public ElementStyledContainer(int x, int y, int width, int height) {
		this(x, y, width, height, TexturedBackground.STYLED_CONTAINER);
	}
	
	public ElementStyledContainer(int width, int height) {
		this(0,0 , width, height);
	}
	
	public ElementStyledContainer(LayoutDimension width, LayoutDimension height) {
		this(width, height, TexturedBackground.STYLED_CONTAINER);
	}
	
	public ElementStyledContainer() {
		this(LayoutDimension.WRAP_CONTENT, LayoutDimension.WRAP_CONTENT);
	}

	@Override
	public void draw(ElementRootContainer root, int mouseX, int mouseY) {
		drawBackgroundFromTextures(background);
		super.draw(root, mouseX, mouseY);
	}
	
}
