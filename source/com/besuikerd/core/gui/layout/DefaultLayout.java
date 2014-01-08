package com.besuikerd.core.gui.layout;

import java.awt.Dimension;

import com.besuikerd.core.gui.element.Element;
import com.besuikerd.core.gui.element.ElementContainer;

public class DefaultLayout implements Layout{

	/**
	 * Maximum width found
	 */
	private int maxWidth;
	
	/**
	 * Maximum height found
	 */
	private int maxHeight;
	
	@Override
	public void init(ElementContainer container) {
		this.maxWidth = 0;
		this.maxHeight = 0;
	}
	
	
	@Override
	public boolean layout(Element e, int index) {
		//keep element within container bounds and apply padding
		
		if(e.getParent().getWidthDimension() != LayoutDimension.WRAP_CONTENT){
			//left bound crossed
			if(e.getX() < e.getParent().getPaddingLeft()){
				e.setX(e.getParent().getPaddingLeft());
			}
			
			//right bound crossed
			if(e.getX() + e.getWidth() + e.getParent().getPaddingRight() > e.getParent().getWidth()){
				e.setX(e.getParent().getWidth() - (e.getWidth() + e.getParent().getPaddingRight()));
			}
		}
		
		if(e.getParent().getHeightDimension() != LayoutDimension.WRAP_CONTENT){
			//top bound crossed
			if(e.getY() < e.getParent().getPaddingTop()){
				e.setY(e.getParent().getPaddingTop());
			}
			
			//bottom bound crossed
			if(e.getY() + e.getHeight() + e.getParent().getPaddingBottom() > e.getParent().getHeight()){
				e.setY(e.getParent().getHeight() - (e.getHeight() + e.getParent().getPaddingBottom()));
			}
		}
		
		if(e.getX() + e.getWidth() > maxWidth){
			this.maxWidth = e.getX() + e.getWidth();
		}
		
		if(e.getY() + e.getHeight() > maxHeight){
			this.maxHeight = e.getY() + e.getHeight();
		}
		
		return true;
	}

	@Override
	public Dimension getLaidOutDimension() {
		return new Dimension(maxWidth, maxHeight);
	}
	
	@Override
	public void align(Element e) {
		switch (e.getAlignment()) {
			case TOP:
				e.setY(e.getParent().getPaddingTop());
				e.setX((e.getParent().getWidth() - e.getWidth()) / 2);
				break;
			case RIGHT:
				e.setY((e.getParent().getHeight() - e.getHeight()) / 2);
				e.setX(e.getParent().getWidth() - e.getParent().getPaddingRight());
				break;
			case BOTTOM:
				e.setY(e.getParent().getHeight() - e.getParent().getPaddingBottom());
				e.setX((e.getParent().getWidth() - e.getWidth()) / 2);
				break;
			case LEFT:
				e.setY((e.getParent().getHeight() - e.getHeight()) / 2);
				e.setX(e.getParent().getPaddingLeft());
				break;
			case CENTER:
				e.setY((e.getParent().getHeight() - e.getHeight()) / 2);
				e.setX((e.getParent().getWidth() - e.getWidth()) / 2);
				break;
			default:
				break;
		}
		
	}
}
