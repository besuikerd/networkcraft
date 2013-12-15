package nl.besuikerd.networkcraft.gui.element;

import org.lwjgl.opengl.GL11;

import nl.besuikerd.networkcraft.core.utils.Tuple;

public class ElementStyledContainer extends ElementContainer{

	protected Tuple texEdgeTop = new Tuple(0, 250, 256, 3);
	protected Tuple texEdgeRight = new Tuple(253, 0, 3, 250);
	protected Tuple texEdgeBottom = new Tuple(0, 253, 256, 3);
	protected Tuple texEdgeLeft = new Tuple(250, 0, 3, 250);
	
	protected Tuple texCornerTL = new Tuple(64, 0, 4, 4);
	protected Tuple texCornerTR = new Tuple(68, 0, 4, 4);
	protected Tuple texCornerBL = new Tuple(64, 4, 4, 4);
	protected Tuple texCornerBR = new Tuple(68, 4, 4, 4);
	protected Tuple texBg = new Tuple(72, 0, 16, 16);
	
	public ElementStyledContainer(int x, int y, int width, int height) {
		super(x, y, width, height);
		paddingTop = texEdgeTop.int4();
		paddingRight = texEdgeRight.int3();
		paddingBottom = texEdgeBottom.int4();
		paddingLeft = texEdgeLeft.int3();
	}

	@Override
	public void draw(ElementContainer parent, int mouseX, int mouseY) {
		mc.getTextureManager().bindTexture(textures);
		GL11.glColor4f(1f, 1f, 1f, 1f);
		drawBackgroundFromTextures(texBg, texEdgeTop, texEdgeRight, texEdgeBottom, texEdgeLeft, texCornerTL, texCornerTR, texCornerBL, texCornerBR);
		super.draw(parent, mouseX, mouseY);
	}
}
