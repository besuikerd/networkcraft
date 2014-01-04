package nl.besuikerd.core.gui.element;

import static org.lwjgl.opengl.GL11.GL_ALWAYS;
import static org.lwjgl.opengl.GL11.GL_EQUAL;
import static org.lwjgl.opengl.GL11.GL_KEEP;
import static org.lwjgl.opengl.GL11.GL_REPLACE;
import static org.lwjgl.opengl.GL11.GL_STENCIL_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_STENCIL_TEST;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glColorMask;
import static org.lwjgl.opengl.GL11.glDepthMask;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glStencilFunc;
import static org.lwjgl.opengl.GL11.glStencilMask;
import static org.lwjgl.opengl.GL11.glStencilOp;
import nl.besuikerd.core.BLogger;
import nl.besuikerd.core.gui.layout.LayoutDimension;

/**
 * Element that can contain a container. the container is only visible within
 * the bounds of the viewport. the container can be larger than the viewport.
 * the viewport acts as a root for the container within the viewport, to make
 * sure mouse events are handled properly
 * 
 * @author Besuikerd
 * 
 */
public class ElementViewport extends Element {

	/**
	 * container that is visible within this viewport
	 */
	protected Element element;
	
	/**
	 * root delegate
	 */
	protected ElementRootContainer root;

	/**
	 * x-offset for container within viewport; can also be negative
	 */
	protected int xOffset;

	/**
	 * y-offset for container within viewport; can also be negative
	 */
	protected int yOffset;
	
	

	public ElementViewport(int width, int height, Element element) {
		super(width, height);
		root = new ElementRootContainer(0, height);
		root.add(element);
		this.element = element;
	}

	@Override
	public void dimension(ElementRootContainer root) {
		super.dimension(root);
		//move container to the correct spot within the viewport
		element.dx = absX();
		element.dy = absY();
		element.x = xOffset;
		element.y = yOffset;

		//layout element and fix dimensions
		element.dimension(root);
		
		if(widthDimension == LayoutDimension.WRAP_CONTENT){
			this.width = element.width;
		}
		if(heightDimension == LayoutDimension.WRAP_CONTENT){
			this.height = element.height;
		}

		//limit root delegate within bounds of the real root
		this.root.width = Math.min(element.width, root.width - root.paddingLeft - root.paddingRight);
		this.root.height = Math.min(height, root.height - root.paddingTop - root.paddingBottom);
		this.root.dx = Math.max(absX(), root.absX() + root.paddingLeft);
		this.root.dy = Math.max(absY(), root.absY() + root.paddingTop);
	}

	@Override
	protected boolean handleMouseInput(ElementRootContainer root, int mouseX, int mouseY) {
		super.handleMouseInput(root, mouseX, mouseY);
		this.root.focusedElement = root.focusedElement; //copy focus from real root
		this.root.scrollMovement = root.scrollMovement; //copy scroll movement from real root
		this.root.eventHandler = root.eventHandler; //copy eventhandler from real root
		return element.handleMouseInput(this.root, mouseX - xOffset, mouseY - yOffset);
	}
	
	@Override
	public void update(ElementRootContainer root) {
		super.update(root);
		element.update(root);
	}
	
	@Override
	public void onEvent(String name, Object[] args, ElementRootContainer root, Element e) {
		element.onEvent(name, args, root, e);
	}

	@Override
	public void draw(ElementRootContainer root, int mouseX, int mouseY) {
		super.draw(root, mouseX, mouseY);
		glEnable(GL_STENCIL_TEST);
		glStencilFunc(GL_ALWAYS, 0x1, 0xff);
		glStencilOp(GL_KEEP, GL_KEEP, GL_REPLACE);
		glColorMask(false, false, false, false); //no color mask
		glDepthMask(false); //no depth mask
		glStencilMask(0xff); // draw to mask
		glClear(GL_STENCIL_BUFFER_BIT); //clear stencil
		glColor4f(1, 1, 1, 1);
		drawRect(Math.max(absX(), root.absX()), Math.max(absY(), root.absY()), Math.min(absX() + width, root.absX() + root.width), Math.min(absY() + height, root.absY() + root.height), 0xffffffff); //draw square mask
		glStencilMask(0x0); //don't draw to mask
		glStencilFunc(GL_EQUAL, 0x1, 0xff);
		glColorMask(true, true, true, true); //restore color mask
		glDepthMask(true); //restore depth mask
		element.draw(this.root, mouseX, mouseY);
		glDisable(GL_STENCIL_TEST);
	}
}
