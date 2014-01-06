package nl.besuikerd.core.gui.element;

import org.lwjgl.opengl.GL11;

import nl.besuikerd.core.gui.layout.LayoutDimension;
import nl.besuikerd.core.gui.texture.scalable.IScalableTexture;
import nl.besuikerd.core.gui.texture.scalable.ScalableTexture;
import nl.besuikerd.core.utils.TupleUtils;

public class ElementStyledContainer extends ElementContainer{

	protected IScalableTexture background;
	
	public ElementStyledContainer(int x, int y, int width, int height, IScalableTexture background) {
		super(x, y, width, height);
		this.background = background;
		paddingTop = TupleUtils.yDiff(background.edgeTop());
		paddingRight = TupleUtils.xDiff(background.edgeRight());
		paddingBottom = TupleUtils.yDiff(background.edgeBottom());
		paddingLeft = TupleUtils.xDiff(background.edgeLeft());
	}
	
	public ElementStyledContainer(int width, int height, IScalableTexture background) {
		this(0, 0, width, height, background);
	}

	public ElementStyledContainer(LayoutDimension width, LayoutDimension height, IScalableTexture background) {
		this(0, 0, 0, 0, background);
		this.widthDimension = width;
		this.heightDimension = height;
	}
	
	public ElementStyledContainer(int x, int y, int width, int height) {
		this(x, y, width, height, ScalableTexture.STYLED_CONTAINER);
	}
	
	public ElementStyledContainer(int width, int height) {
		this(0,0 , width, height);
	}
	
	public ElementStyledContainer(LayoutDimension width, LayoutDimension height) {
		this(width, height, ScalableTexture.STYLED_CONTAINER);
	}
	
	public ElementStyledContainer() {
		this(LayoutDimension.WRAP_CONTENT, LayoutDimension.WRAP_CONTENT);
	}

	@Override
	public void draw(ElementRootContainer root, int mouseX, int mouseY) {
		bindTexture();
		drawBackgroundFromTextures(background);
		super.draw(root, mouseX, mouseY);
	}
	
}
