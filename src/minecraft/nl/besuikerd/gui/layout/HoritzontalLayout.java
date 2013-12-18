package nl.besuikerd.gui.layout;

import java.awt.Dimension;
import java.util.Stack;

import nl.besuikerd.core.BLogger;
import nl.besuikerd.gui.element.Element;
import nl.besuikerd.gui.element.ElementContainer;

public class HoritzontalLayout implements Layout{

	private int xOffset;
	private int yOffset;
	
	protected int marginX;
	protected int marginY;
	
	private boolean init;
	
	public HoritzontalLayout(int marginX, int marginY) {
		this.marginX = marginX;
		this.marginY = marginY;
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
	public void init(ElementContainer parent, int mouseX, int mouseY) {
		xOffset = parent.getPaddingLeft();
		yOffset = parent.getPaddingTop();
	}
	
	@Override
	public boolean layout(ElementContainer container, Element e, int index, int mouseX,
			int mouseY) {
		
		//check if element would fall out of horizontal bounds		
		if(xOffset + e.getWidth() > container.getWidth() - container.getPaddingRight()){
			xOffset = container.getPaddingLeft();
			yOffset += e.getHeight() + marginY;
		}
		
		//not allowed to render when the container is filled entirely
		if(yOffset + e.getHeight() > container.getHeight() - container.getPaddingBottom()){
			return false;
		}
		e.setX(xOffset);
		e.setY(yOffset);
		
		//increment xOffset
		xOffset += e.getWidth() + marginX;
		return true;
	}
	
	@Override
	public Dimension getLaidOutDimension() {
		return new Dimension(xOffset, yOffset);
	}
}
