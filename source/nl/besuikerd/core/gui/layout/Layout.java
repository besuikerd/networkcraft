package nl.besuikerd.core.gui.layout;

import java.awt.Dimension;

import nl.besuikerd.core.gui.element.Element;
import nl.besuikerd.core.gui.element.ElementContainer;

public interface Layout {
	
	/**
	 * Called before elements needs to be laid out; used to reset the state of the layout before laying out all Elements.
	 * @param container
	 * @param mouseX
	 * @param mouseY
	 */
	public void init(ElementContainer container, ElementContainer root);
	
	/**
	 * translate the x and y coordinates of the Element in the given ElementContainer.
	 * @param container ElementContainer the element is in
	 * @param e Element that needs to be laid out
	 * @param index index of the Element in the ElementContainer
	 * @return
	 */
	public boolean layout(ElementContainer container, Element e, int index, ElementContainer root);
	
	/**
	 * The dimensions of Elements laid out so far by this Layout
	 * @return The Dimension of Elements laid out so far by this Layout
	 */
	public Dimension getLaidOutDimension();
	
	/**
	 * align an Element relative to the container
	 * @param e
	 */
	public void align(Element e, ElementContainer parent);
}
