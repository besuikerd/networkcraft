package nl.besuikerd.core.gui.event;

import nl.besuikerd.core.gui.element.Element;
import nl.besuikerd.core.gui.element.ElementRootContainer;

public interface ITrigger {
	public void trigger(String name, ElementRootContainer root, Element e, Object... args);
}
