package nl.besuikerd.core.gui.texture;

import nl.besuikerd.core.utils.Tuple;

public enum TexturedBackground implements ITexturedBackground{
	STYLED_CONTAINER(new Tuple(72, 0, 95, 23), new Tuple(0, 250, 255, 252), new Tuple(253, 0, 255, 249), new Tuple(0, 253, 255, 255), new Tuple(250, 0, 252, 249), new Tuple(64, 0, 67, 3), new Tuple(68, 0, 71, 3), new Tuple(68, 4, 71, 7), new Tuple(64, 4, 67, 7)),
	CONTAINER(new Tuple(47, 1, 62, 16), new Tuple(47, 0, 62, 0), new Tuple(63, 1, 63, 16), new Tuple(47, 17, 62, 17), new Tuple(46, 1, 46, 16), new Tuple(46, 0, 46, 0), new Tuple(63, 0, 63, 0), new Tuple(63, 17, 63, 17), new Tuple(46, 17, 46, 17))
	;
	
	protected Tuple background;
	
	protected Tuple edgeTop;
	protected Tuple edgeRight;
	protected Tuple edgeBottom;
	protected Tuple edgeLeft;

	protected Tuple cornerTL;
	protected Tuple cornerTR;
	protected Tuple cornerBR;
	protected Tuple cornerBL;
	
	private TexturedBackground(Tuple background, Tuple edgeTop, Tuple edgeRight, Tuple edgeBottom, Tuple edgeLeft, Tuple cornerTL, Tuple cornerTR, Tuple cornerBR, Tuple cornerBL) {
		this.background = background;
		this.edgeTop = edgeTop;
		this.edgeRight = edgeRight;
		this.edgeBottom = edgeBottom;
		this.edgeLeft = edgeLeft;
		this.cornerTL = cornerTL;
		this.cornerTR = cornerTR;
		this.cornerBR = cornerBR;
		this.cornerBL = cornerBL;
	}

	@Override
	public Tuple background() {
		return background;
	}

	@Override
	public Tuple edgeTop() {
		return edgeTop;
	}

	@Override
	public Tuple edgeRight() {
		return edgeRight;
	}

	@Override
	public Tuple edgeBottom() {
		return edgeBottom;
	}

	@Override
	public Tuple edgeLeft() {
		return edgeLeft;
	}

	@Override
	public Tuple cornerTL() {
		return cornerTL;
	}

	@Override
	public Tuple cornerTR() {
		return cornerTR;
	}

	@Override
	public Tuple cornerBR() {
		return cornerBR;
	}

	@Override
	public Tuple cornerBL() {
		return cornerBL;
	}
}
