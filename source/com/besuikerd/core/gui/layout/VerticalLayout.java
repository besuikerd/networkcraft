package com.besuikerd.core.gui.layout;

import java.awt.Dimension;

import com.besuikerd.core.gui.element.Element;
import com.besuikerd.core.gui.element.ElementContainer;

public class VerticalLayout implements Layout {
	private int xOffset;
	private int yOffset;

	/**
	 * maximum width of the elements in the rightmost column
	 */
	private int maxWidth;

	protected int marginX;
	protected int marginY;

	private boolean init;

	public VerticalLayout(int marginX, int marginY) {
		this.marginX = marginX;
		this.marginY = marginY;
	}

	public VerticalLayout() {
		this(0, 0);
	}

	public VerticalLayout marginX(int x) {
		this.marginX = x;
		return this;
	}

	public VerticalLayout marginY(int y) {
		this.marginY = y;
		return this;
	}

	@Override
	public void init(ElementContainer parent) {
		xOffset = parent.getPaddingLeft();
		yOffset = parent.getPaddingTop();
		maxWidth = 0;
	}

	@Override
	public boolean layout(Element e, int index) {

		//check if element would fall out of vertical bounds
		if (e.getParent().getHeightDimension() != LayoutDimension.WRAP_CONTENT && yOffset + e.getHeight() > e.getParent().getHeight() - e.getParent().getPaddingBottom()) {
			yOffset = e.getParent().getPaddingTop();
			xOffset += e.getWidth() + marginX;
			maxWidth = e.getWidth();
		} else if (e.getWidth() > maxWidth) {
			maxWidth = e.getWidth();
		}

		e.setX(xOffset);
		e.setY(yOffset);

		//increment xOffset
		yOffset += e.getHeight() + (e.getParent().getElementCount() - 1 == index ? 0 : marginY);
		return true;
	}

	@Override
	public Dimension getLaidOutDimension() {
		return new Dimension(xOffset + maxWidth, yOffset);
	}

	@Override
	public void align(Element e) {
		if (xOffset == e.getParent().getPaddingLeft()) { //check if there is only a single column
			switch (e.getAlignment()) {
				case RIGHT:
					e.setX(e.getParent().getWidth() - e.getWidth() - e.getParent().getPaddingRight());
					break;
				case CENTER:
					e.setX((e.getParent().getWidth() - e.getWidth()) / 2);
					break;
				default:
					break;
			}
		}
	}
}
