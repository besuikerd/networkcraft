package nl.besuikerd.core.gui.texture;

import nl.besuikerd.core.utils.Tuple;

public enum TexturedBackgroundVerticalScroller implements ITexturedBackground{
	NORMAL(new Tuple(98, 1, 105, 2), new Tuple(98, 0, 105, 1), new Tuple(106, 2, 107, 12), new Tuple(98, 13, 105, 14), new Tuple(96, 2, 97, 12), new Tuple(96, 0, 97, 1), new Tuple(106, 0, 107, 1), new Tuple(106, 13, 107, 14), new Tuple(96, 13, 97, 14)),
	ACTIVATED(new Tuple(110, 1, 117, 2), new Tuple(110, 0, 117, 1), new Tuple(118, 2, 119, 12), new Tuple(110, 13, 117, 14), new Tuple(108, 2, 109, 12), new Tuple(108, 0, 109, 1), new Tuple(118, 0, 119, 1), new Tuple(118, 13, 119, 14), new Tuple(108, 13, 109, 14)),
	DISABLED(new Tuple(122, 1, 129, 2), new Tuple(122, 0, 129, 1), new Tuple(130, 2, 131, 12), new Tuple(122, 13, 129, 14), new Tuple(120, 2, 121, 12), new Tuple(120, 0, 121, 1), new Tuple(130, 0, 131, 1), new Tuple(130, 13, 131, 14), new Tuple(120, 13, 121, 14))
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
	
	private TexturedBackgroundVerticalScroller(Tuple background, Tuple edgeTop, Tuple edgeRight, Tuple edgeBottom, Tuple edgeLeft, Tuple cornerTL, Tuple cornerTR, Tuple cornerBR, Tuple cornerBL) {
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
