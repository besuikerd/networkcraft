package nl.besuikerd.core.gui.layout;

import java.awt.Dimension;

import nl.besuikerd.core.gui.element.Element;
import nl.besuikerd.core.gui.element.ElementContainer;

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
	public void init(ElementContainer container, ElementContainer root) {
		this.maxWidth = 0;
		this.maxHeight = 0;
	}
	
	
	@Override
	public boolean layout(ElementContainer container, Element e, int index, ElementContainer root) {
		//keep element within container bounds and apply padding
		
		if(container.getWidthDimension() != LayoutDimension.WRAP_CONTENT){
			//left bound crossed
			if(e.getX() < container.getPaddingLeft()){
				e.setX(container.getPaddingLeft());
			}
			
			//right bound crossed
			if(e.getX() + e.getWidth() + container.getPaddingRight() > container.getWidth()){
				e.setX(container.getWidth() - (e.getWidth() + container.getPaddingRight()));
			}
		}
		
		if(container.getHeightDimension() != LayoutDimension.WRAP_CONTENT){
			//top bound crossed
			if(e.getY() < container.getPaddingTop()){
				e.setY(container.getPaddingTop());
			}
			
			//bottom bound crossed
			if(e.getY() + e.getHeight() + container.getPaddingBottom() > container.getHeight()){
				e.setY(container.getHeight() - (e.getHeight() + container.getPaddingBottom()));
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
	public void align(Element e, ElementContainer parent) {
		switch (e.getAlignment()) {
			case TOP:
				e.setY(parent.getPaddingTop());
				e.setX((parent.getWidth() - e.getWidth()) / 2);
				break;
			case RIGHT:
				e.setY((parent.getHeight() - e.getHeight()) / 2);
				e.setX(parent.getWidth() - parent.getPaddingRight());
				break;
			case BOTTOM:
				e.setY(parent.getHeight() - parent.getPaddingBottom());
				e.setX((parent.getWidth() - e.getWidth()) / 2);
				break;
			case LEFT:
				e.setY((parent.getHeight() - e.getHeight()) / 2);
				e.setX(parent.getPaddingLeft());
				break;
			case CENTER:
				e.setY((parent.getHeight() - e.getHeight()) / 2);
				e.setX((parent.getWidth() - e.getWidth()) / 2);
				break;
			default:
				break;
		}
		
	}
}
