package nl.besuikerd.networkcraft.gui.layout;

import nl.besuikerd.networkcraft.gui.element.Element;
import nl.besuikerd.networkcraft.gui.element.ElementContainer;

public class DefaultLayout implements Layout{

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
		return true;
	}

	@Override
	public void init(ElementContainer container, int mouseX, int mouseY) {
		//nothing to do; this layout is stateless
	}
	
}
