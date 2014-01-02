package nl.besuikerd.core.gui.element;

import javax.swing.GroupLayout.Alignment;

import nl.besuikerd.core.BLogger;
import nl.besuikerd.core.gui.layout.HorizontalLayout;
import nl.besuikerd.core.gui.layout.LayoutDimension;

public class ElementScrollContainer extends ElementContainer{
	protected ElementViewport viewport;
	protected ElementScrollBar scrollBar;
	
	@Override
	protected boolean onScrolled(ElementRootContainer root, int x, int y, int amount) {
		BLogger.debug("SCROLLED!");
		return scrollBar.onScrolled(root, x, y, amount);
	}
	
	public ElementScrollContainer(int height, final ElementContainer container) {
		super(LayoutDimension.WRAP_CONTENT, LayoutDimension.ABSOLUTE);
		this.height = height;
		this.layout = new HorizontalLayout();
		this.viewport = new ElementViewport(height, container);
		this.scrollBar = new ElementScrollBar(10){
			@Override
			public void onProgressChange(double old, double progress) {
				viewport.yOffset = (int) (-1 * (container.height - this.height) * progress);
			}
		};
		
		add(viewport);
		add(scrollBar);
	}
}
