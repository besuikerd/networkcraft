package com.besuikerd.core.gui.texture.scalable;

import com.besuikerd.core.utils.Tuple;

public enum ScalableTextureSolidButton implements IScalableTexture{
	NORMAL(new Tuple(133, 1, 148, 16), new Tuple(133, 0, 148, 0), new Tuple(149, 1, 149, 16), new Tuple(133, 17, 148, 17), new Tuple(132, 1, 132, 16), new Tuple(132, 0, 132, 0), new Tuple(149, 0, 149, 0), new Tuple(149, 17, 149, 17), new Tuple(132, 17, 132, 17)),
	ACTIVATED(new Tuple(47, 1, 62, 16), new Tuple(47, 0, 62, 0), new Tuple(63, 1, 63, 16), new Tuple(47, 17, 62, 17), new Tuple(46, 1, 46, 16), new Tuple(46, 0, 46, 0), new Tuple(63, 0, 63, 0), new Tuple(63, 17, 63, 17), new Tuple(46, 17, 46, 17)),
	DISABLED(new Tuple(151, 1, 166, 16), new Tuple(151, 0, 166, 0), new Tuple(167, 1, 167, 16), new Tuple(151, 17, 166, 17), new Tuple(150, 1, 150, 16), new Tuple(150, 0, 167, 0), new Tuple(167, 0, 167, 0), new Tuple(167, 17, 167, 17), new Tuple(150, 17, 150, 17))
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
	
	private ScalableTextureSolidButton(Tuple background, Tuple edgeTop, Tuple edgeRight, Tuple edgeBottom, Tuple edgeLeft, Tuple cornerTL, Tuple cornerTR, Tuple cornerBR, Tuple cornerBL) {
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
	public Tuple getTexture() {
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
