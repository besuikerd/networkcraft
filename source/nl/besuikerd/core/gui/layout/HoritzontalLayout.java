package nl.besuikerd.core.gui.layout;

import java.awt.Dimension;
import java.util.Stack;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

import nl.besuikerd.core.BLogger;
import nl.besuikerd.core.gui.element.Element;
import nl.besuikerd.core.gui.element.ElementContainer;

public class HoritzontalLayout implements Layout{

	private int xOffset;
	private int yOffset;
	
	/**
	 * maximum height of the elements in the lowest row
	 */
	private int maxHeight;
	
	
	protected int marginX;
	protected int marginY;
	
	private boolean init;
	
	public HoritzontalLayout(int marginX, int marginY) {
		this.marginX = marginX;
		this.marginY = marginY;
		maxHeight = 0;
	}
	
	public HoritzontalLayout(){
		this(0, 0);
	}
	
	public HoritzontalLayout marginX(int x){
		this.marginX = x;
		return this;
	}
	
	public HoritzontalLayout marginY(int y){
		this.marginY = y;
		return this;
	}
	
	@Override
	public void init(ElementContainer parent, ElementContainer root) {
		xOffset = parent.getPaddingLeft();
		yOffset = parent.getPaddingTop();
	}
	
	@Override
	public boolean layout(ElementContainer container, Element e, int index, ElementContainer root) {
		
		//check if element would fall out of horizontal bounds		
		if(container.getWidthDimension() != LayoutDimension.WRAP_CONTENT && xOffset + e.getWidth() > container.getWidth() - container.getPaddingRight()){
			xOffset = container.getPaddingLeft();
			yOffset += e.getHeight() + marginY;
			maxHeight = e.getHeight();
		} else if(e.getHeight() > maxHeight){
			maxHeight = e.getHeight();
		}
		
		//layout element coordinates
		e.setX(xOffset);
		e.setY(yOffset);
		
		//increment xOffset
		xOffset += e.getWidth() + (container.getElementCount() - 1 == index ? 0 : marginX);
		return true;
	}
	
	@Override
	public Dimension getLaidOutDimension() {
		return new Dimension(xOffset, yOffset + maxHeight);
	}
	
	@Override
	public void align(Element e, ElementContainer parent) {
		if (yOffset == parent.getPaddingTop()) { //check if there is only a single row
			switch (e.getAlignment()) {
				case BOTTOM:
					e.setY(parent.getHeight() - e.getHeight() - parent.getPaddingBottom());
					break;
				case CENTER:
					e.setY((parent.getHeight() - e.getHeight()) / 2);
					break;
				default:
					break;
			}
		}
	}
}
