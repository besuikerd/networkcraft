package nl.besuikerd.core.gui.event;

import nl.besuikerd.core.gui.element.Element;
import nl.besuikerd.core.gui.element.ElementRootContainer;

public interface IEventAction {
	public void onEvent(String name, Object[] args, ElementRootContainer root, Element e);
}
