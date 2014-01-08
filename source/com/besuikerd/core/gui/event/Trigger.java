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
	
	/**
	 * element is being updated
	 */
	UPDATE,
	
	KEY_PRESSED,
	KEY_TYPED,
	KEY_RELEASED,
	
	/**
	 * element checked/unchecked
	 */
	CHECKED,
	
	/**
	 * element toggled on/off
	 */
	TOGGLED,
	
	/**
	 * progress change
	 */
	PROGRESS_CHANGED
	
	;

	
	@Override
	public void trigger(String name, ElementRootContainer root, Element e, Object... args) {
		root.getEventHandler().post(name, e, args);
	}
	
}
