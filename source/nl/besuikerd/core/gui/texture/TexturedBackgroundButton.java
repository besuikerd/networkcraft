package nl.besuikerd.core.gui.texture;

import nl.besuikerd.core.utils.Tuple;

public enum TexturedBackgroundButton implements ITexturedBackground{
	DISABLED(new Tuple(1, 25, 198, 42), new Tuple(1, 24, 198, 24), new Tuple(199, 25, 199, 42), new Tuple(1, 43, 198, 43), new Tuple(0, 25, 0, 42), new Tuple(0, 24, 0, 24), new Tuple(199, 24, 199, 24), new Tuple(0, 43, 0, 43), new Tuple(199, 43, 199, 43)),
	NORMAL(new Tuple(2, 46, 197, 60), new Tuple(2, 44, 197, 45), new Tuple(198, 46, 199, 60), new Tuple(2, 61, 197, 63), new Tuple(0, 46, 1, 60), new Tuple(0, 44, 1, 45), new Tuple(198, 44, 199, 45), new Tuple(198, 61, 199, 63), new Tuple(0, 61, 1, 63)),
	HOVERING(new Tuple(2, 66, 197, 80), new Tuple(2, 64, 197, 65), new Tuple(198, 66, 199, 80), new Tuple(2, 81, 197, 83), new Tuple(0, 66, 1, 80), new Tuple(0, 64, 1, 65), new Tuple(198, 64, 199, 65), new Tuple(198, 81, 199, 83), new Tuple(0, 81, 1, 83)),
	ACTIVATED(new Tuple(2, 87, 197, 101), new Tuple(2, 84, 197, 86), new Tuple(198, 87, 199, 101), new Tuple(2, 102, 197, 103), new Tuple(0, 87, 1, 101), new Tuple(0, 84, 1, 86), new Tuple(198, 84, 199, 86), new Tuple(198, 102, 199, 103), new Tuple(0, 102, 1, 103));
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
	
	private TexturedBackgroundButton(Tuple background, Tuple edgeTop, Tuple edgeRight, Tuple edgeBottom, Tuple edgeLeft, Tuple cornerTL, Tuple cornerTR, Tuple cornerBR, Tuple cornerBL) {
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
