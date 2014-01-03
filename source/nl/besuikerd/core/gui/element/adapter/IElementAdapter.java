package nl.besuikerd.core.gui.element.adapter;

import nl.besuikerd.core.gui.element.Element;

public interface IElementAdapter {
	public Element getElementAt(int index);
	public int getElementCount();
	public void addInvalidationListener(InvalidationListener listener);
	public void removeInvalidationListener(InvalidationListener listener);
}
