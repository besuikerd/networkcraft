package nl.besuikerd.gui.layout;

import java.awt.Dimension;

import nl.besuikerd.gui.element.Element;
import nl.besuikerd.gui.element.ElementContainer;

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
	public boolean layout(ElementContainer container, Element e, int index,
			int mouseX, int mouseY) {
		//keep element within container bounds and apply padding
		
		//left bound crossed
		if(e.getX() < container.getPaddingLeft()){
			e.setX(container.getPaddingLeft());
		}
		
		//top bound crossed
		if(e.getY() < container.getPaddingTop()){
			e.setY(container.getPaddingTop());
		}
		
		//right bound crossed
		if(e.getX() + e.getWidth() + container.getPaddingRight() > container.getWidth()){
			e.setX(container.getWidth() - (e.getWidth() + container.getPaddingRight()));
		}
		
		//left bound crossed
		if(e.getY() + e.getHeight() + container.getPaddingBottom() > container.getHeight()){
			e.setY(container.getHeight() - (e.getHeight() + container.getPaddingBottom()));
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
	public void init(ElementContainer container, int mouseX, int mouseY) {
		this.maxWidth = 0;
		this.maxHeight = 0;
	}
	
	@Override
	public Dimension getLaidOutDimension() {
		return new Dimension(maxWidth, maxHeight);
	}
	
}
