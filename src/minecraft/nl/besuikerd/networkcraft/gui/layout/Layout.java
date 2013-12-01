package nl.besuikerd.networkcraft.gui.layout;

import nl.besuikerd.networkcraft.gui.element.Element;
import nl.besuikerd.networkcraft.gui.element.ElementContainer;

public interface Layout {
	
	public void init(ElementContainer container, int mouseX, int mouseY);
	public boolean layout(ElementContainer container, Element e, int index, int mouseX, int mouseY);
}
