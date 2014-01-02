package nl.besuikerd.core.gui.element;

import net.minecraft.client.renderer.Tessellator;
import nl.besuikerd.core.BLogger;
import nl.besuikerd.core.utils.MathUtils;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import static org.lwjgl.opengl.GL11.*;

public class ElementViewport extends Element{

	protected ElementContainer container;
	protected ElementContainer root;
	
	protected int xOffset;
	protected int yOffset;
	
	
	public ElementViewport(int height, ElementContainer container) {
		super(0, height);
		root = new ElementContainer(0, height);
		root.add(container);
		this.container = container;
	}
	
	@Override
	public void dimension(ElementContainer parent, ElementContainer root) {
		super.dimension(parent, root);
		container.dx = absX();
		container.dy = absY();
		container.x = xOffset;
		container.y = yOffset;
		
		container.dimension(parent, root);
		this.width = container.width;
		this.root.width = container.width;
		this.root.dx = absX();
		this.root.dy = absY();
//		BLogger.debug("width: %d", this.width);
	}
	
	@Override
	protected boolean keyTyped(char key, int code, ElementContainer root) {
		return container.keyTyped(key, code, root);
	}
	
	@Override
	public void update(ElementContainer parent, ElementContainer root, int mouseX, int mouseY) {
		container.update(parent, root, mouseX, mouseY);
	}
	
	@Override
	public boolean handleKeyboardInput() {
		return container.handleKeyboardInput();
	}
	
	@Override
	protected boolean handleMouseInput(ElementContainer parent, ElementContainer root, int x, int y) {
		return container.handleMouseInput(parent, this.root, x, y);
	}
	
	@Override
	protected void onScrolled(ElementContainer parent, int x, int y, int amount) {
		container.onScrolled(parent, x, y, amount);
	}

	@Override
	public void draw(ElementContainer parent, int mouseX, int mouseY, ElementContainer root) {
		super.draw(parent, mouseX, mouseY, root);
		
		glEnable(GL_STENCIL_TEST);
		glStencilFunc(GL_ALWAYS, 0x1, 0xff);
		glStencilOp(GL_KEEP, GL_KEEP, GL_REPLACE);
		glColorMask(false, false, false, false); //no color mask
		glDepthMask(false); //no depth mask
		glStencilMask(0xff); // draw to mask
		glClear(GL_STENCIL_BUFFER_BIT); //clear stencil
		glColor4f(1, 1, 1, 1);
		drawRect(absX(), absY(), absX() + width, absY() + height, 0xffffffff); //draw square mask
		glStencilMask(0x0); //don't draw to mask
		glStencilFunc(GL_EQUAL, 0x1, 0xff);
		glColorMask(true, true, true, true); //restore color mask
		glDepthMask(true); //restore depth mask
		container.draw(parent, mouseX, mouseY, root);
		glDisable(GL_STENCIL_TEST);
	}
}
