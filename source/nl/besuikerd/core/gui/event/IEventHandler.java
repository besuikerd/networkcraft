package nl.besuikerd.core.gui.event;

import nl.besuikerd.core.gui.element.Element;

public interface IEventHandler {
	public void post(String name, Element e, Object... args);
}
