package com.besuikerd.core.gui.layout;

import java.awt.Dimension;

import com.besuikerd.core.gui.element.Element;
import com.besuikerd.core.gui.element.ElementContainer;

public class HorizontalLayout implements Layout{

	private int xOffset;
	private int yOffset;
	
	/**
	 * maximum height of the elements in the lowest row
	 */
	private int maxHeight;
	
	
	protected int marginX;
	protected int marginY;
	
	public HorizontalLayout(int marginX, int marginY) {
		this.marginX = marginX;
		this.marginY = marginY;
		maxHeight = 0;
	}
	
	public HorizontalLayout(){
		this(0, 0);
	}
	
	public HorizontalLayout marginX(int x){
		this.marginX = x;
		return this;
	}
	
	public HorizontalLayout marginY(int y){
		this.marginY = y;
		return this;
	}
	
	@Override
	public void init(ElementContainer parent) {
		xOffset = parent.getPaddingLeft();
		yOffset = parent.getPaddingTop();
		maxHeight = 0;
	}
	
	@Override
	public boolean layout(Element e, int index) {
		
		//check if element would fall out of horizontal bounds		
		if(e.getParent().getWidthDimension() != LayoutDimension.WRAP_CONTENT && xOffset + e.getWidth() > e.getParent().getWidth() - e.getParent().getPaddingRight()){
			xOffset = e.getParent().getPaddingLeft();
			yOffset += e.getHeight() + marginY;
			maxHeight = e.getHeight();
		} else if(e.getHeight() > maxHeight){
			maxHeight = e.getHeight();
		}
		
		//layout element coordinates
		e.setX(xOffset);
		e.setY(yOffset);
		
		//increment xOffset
		xOffset += e.getWidth() + (e.getParent().getElementCount() - 1 == index ? 0 : marginX);
		return true;
	}
	
	@Override
	public Dimension getLaidOutDimension() {
		return new Dimension(xOffset, yOffset + maxHeight);
	}
	
	@Override
	public void align(Element e) {
		if (yOffset == e.getParent().getPaddingTop()) { //check if there is only a single row
			switch (e.getAlignment()) {
				case BOTTOM:
					e.setY(e.getParent().getHeight() - e.getHeight() - e.getParent().getPaddingBottom());
					break;
				case CENTER:
					e.setY((e.getParent().getHeight() - e.getHeight()) / 2);
					break;
				default:
					break;
			}
		}
	}
}
