package nl.besuikerd.core.gui.event;

import nl.besuikerd.core.gui.element.Element;
import nl.besuikerd.core.gui.element.ElementRootContainer;

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
	KEY_RELEASED
	;

	
	@Override
	public void trigger(String name, ElementRootContainer root, Element e, Object... args) {
		root.getEventHandler().post(name, e, args);
	}
	
}
