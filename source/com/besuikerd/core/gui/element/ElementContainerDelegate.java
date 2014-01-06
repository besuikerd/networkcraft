package com.besuikerd.core.gui.element;

/**
 * delegates all callbacks towards a local container element, but is not an
 * instance of ElementContainer
 * 
 * @author Besuikerd
 * 
 */
public class ElementContainerDelegate extends Element {
	protected ElementContainer container;

	public ElementContainerDelegate(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	public ElementContainerDelegate(int width, int height) {
		super(width, height);
	}

	@Override
	public void dimension(ElementRootContainer root) {
		container.dx = absX();
		container.dy = absY();
		container.dimension(root);
		this.width = container.width;
		this.height = container.height;
	}

	@Override
	public void draw(ElementRootContainer root, int mouseX, int mouseY) {
		container.draw(root, mouseX, mouseY);
	}

	@Override
	public void update(ElementRootContainer root) {
		container.update(root);
	}

	@Override
	public void onEvent(String name, Object[] args, ElementRootContainer root, Element e) {
		container.onEvent(name, args, root, e);
	}

	@Override
	protected boolean handleMouseInput(ElementRootContainer root, int mouseX, int mouseY) {
		return container.handleMouseInput(root, mouseX, mouseY);
	}

	@Override
	public boolean handleKeyboardInput(ElementRootContainer root) {
		return container.handleKeyboardInput(root);
	}
}
