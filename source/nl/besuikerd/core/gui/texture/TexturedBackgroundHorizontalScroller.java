package nl.besuikerd.core.gui.texture;

import nl.besuikerd.core.utils.Tuple;

public enum TexturedBackgroundHorizontalScroller implements ITexturedBackground{
	NORMAL(new Tuple(170, 2, 171, 9), new Tuple(170, 0, 180, 1), new Tuple(181, 2, 182, 9), new Tuple(170, 10, 180, 11), new Tuple(168, 2, 169, 9), new Tuple(168, 0, 169, 1), new Tuple(181, 0, 182, 1), new Tuple(181, 10, 182, 11), new Tuple(168, 10, 169, 11)),
	ACTIVATED(new Tuple(185, 2, 186, 9), new Tuple(185, 0, 195, 1), new Tuple(196, 2, 197, 9), new Tuple(185, 10, 195, 11), new Tuple(183, 2, 184, 9), new Tuple(183, 0, 184, 1), new Tuple(196, 0, 197, 1), new Tuple(196, 10, 197, 11), new Tuple(183, 10, 184, 11)),
	DISABLED(new Tuple(200, 2, 201, 9), new Tuple(200, 0, 210, 1), new Tuple(211, 2, 212, 9), new Tuple(200, 10, 210, 11), new Tuple(198, 2, 199, 9), new Tuple(198, 0, 199, 1), new Tuple(211, 0, 212, 1), new Tuple(211, 10, 212, 11), new Tuple(197, 10, 198, 11))
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
	
	private TexturedBackgroundHorizontalScroller(Tuple background, Tuple edgeTop, Tuple edgeRight, Tuple edgeBottom, Tuple edgeLeft, Tuple cornerTL, Tuple cornerTR, Tuple cornerBR, Tuple cornerBL) {
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
