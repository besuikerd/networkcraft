package com.besuikerd.core.gui.layout;

import java.awt.Dimension;

import com.besuikerd.core.gui.element.Element;
import com.besuikerd.core.gui.element.ElementContainer;

public class DummyLayout implements Layout{

	private int maxWidth;
	private int maxHeight;
	
	@Override
	public void init(ElementContainer container, ElementContainer root) {
		maxWidth = 0;
		maxHeight = 0;
	}

	@Override
	public boolean layout(ElementContainer container, Element e, int index, ElementContainer root) {
		if(e.getWidth() > maxWidth){
			maxWidth = e.getWidth();
		}
		
		if(e.getHeight() > maxHeight){
			maxHeight = e.getHeight();
		}
		
		return false;
	}

	@Override
	public Dimension getLaidOutDimension() {
		return new Dimension(maxWidth, maxHeight);
	}

	@Override
	public void align(Element e, ElementContainer parent) {
	}
	
}
