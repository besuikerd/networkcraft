package com.besuikerd.core.gui.event;

import com.besuikerd.core.gui.element.Element;
import com.besuikerd.core.gui.element.ElementRootContainer;

public enum Trigger implements ITrigger{
	
	PRESSED,
	DOUBLE_PRESSED,
	RELEASED,
	SCROLLED,
	HOVER,
	MOVE,
	FOCUS,
	FOCUSLOST,
	
	KEY_PRESSED,
	KEY_TYPED,
	KEY_RELEASED,
	CHECKED,
	TOGGLED,
	;

	
	@Override
	public void trigger(String name, ElementRootContainer root, Element e, Object... args) {
		root.getEventHandler().post(name, e, args);
	}
	
}
