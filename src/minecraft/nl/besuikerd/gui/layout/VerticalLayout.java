package nl.besuikerd.gui.layout;

import java.awt.Dimension;
import java.util.Stack;

import nl.besuikerd.core.BLogger;
import nl.besuikerd.gui.element.Element;
import nl.besuikerd.gui.element.ElementContainer;

public class VerticalLayout implements Layout{
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
	
	public VerticalLayout(){
		this(0, 0);
	}
	
	public VerticalLayout marginX(int x){
		this.marginX = x;
		return this;
	}
	
	public VerticalLayout marginY(int y){
		this.marginY = y;
		return this;
	}
	
	@Override
	public void init(ElementContainer parent, int mouseX, int mouseY) {
		xOffset = parent.getPaddingLeft();
		yOffset = parent.getPaddingTop();
		maxWidth = 0;
	}
	
	@Override
	public boolean layout(ElementContainer container, Element e, int index, int mouseX,
			int mouseY) {
		
		//check if element would fall out of vertical bounds
		if(container.getHeightDimension() != LayoutDimension.WRAP_CONTENT && yOffset + e.getHeight() > container.getHeight() - container.getPaddingBottom()){
			yOffset = container.getPaddingTop();
			xOffset += e.getWidth() + marginX;
			maxWidth = e.getWidth();
		} else if(e.getWidth() > maxWidth){
			maxWidth = e.getWidth();
		}
		
		e.setX(xOffset);
		e.setY(yOffset);
		
		//increment xOffset
		yOffset += e.getHeight() + marginY;
		return true;
	}
	
	@Override
	public Dimension getLaidOutDimension() {
		return new Dimension(xOffset + maxWidth, yOffset);
	}
}
