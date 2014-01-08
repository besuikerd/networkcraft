package com.besuikerd.core.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.besuikerd.core.gui.element.Element;
import com.besuikerd.core.gui.element.ElementContainer;
import com.besuikerd.core.gui.element.ElementRootContainer;
import com.besuikerd.core.gui.event.IEventHandler;
import com.besuikerd.core.gui.layout.VerticalLayout;
import com.besuikerd.core.inventory.ContainerBesu;

public class GuiBase extends GuiContainer implements IEventHandler{
	
	protected ElementRootContainer root;
	
	public GuiBase(ContainerBesu container) {
		super(container);
		root = new ElementRootContainer();
		root.setEventHandler(this);
		root.layout(new VerticalLayout())
		.padding(5);
		init();
		root.dimension();
		xSize = root.getWidth();
		ySize = root.getHeight();
		this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
	}

	private static final ResourceLocation bg = new ResourceLocation("textures/gui/demo_background.png");

	/**
	 * Override this method to attach Elements to the {@link #root} container
	 */
	public void init(){}
	
	@Override
	protected void keyTyped(char key, int code) {
		super.keyTyped(key, code);
	}
	
	@Override
	public void handleMouseInput() {
		super.handleMouseInput();
		
        int x = Mouse.getEventX() * this.width / this.mc.displayWidth;
        int y = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
        
        //delegate mouse input to root container
        root.handleMouseInput(x, y);
    }
	
	@Override
	public void handleKeyboardInput() {
		if(!root.handleKeyboardInput() || Keyboard.getEventKey() == Keyboard.KEY_ESCAPE){ //if the root element consumes input, do not let others handle keyboard input
			super.handleKeyboardInput();
		}
	}
	
	public ElementContainer getRoot() {
		return root;
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY) {
		//update all elements before rendering
		root.update();
		
		//dimension all elements in the root container
		root.dimension();
		
		//recalculate size of the root container
		xSize = root.getWidth();
		ySize = root.getHeight();

		//center the root container
		root.setX((width - root.getWidth()) / 2);
		root.setY((height - root.getHeight()) / 2);
		
		root.dimension();
		
		xSize = root.getWidth();
		ySize = root.getHeight();
		this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
		
		mc.getTextureManager().bindTexture(root.getTextures());//TODO move this to a proper location
		GL11.glColor4f(1f, 1f, 1f, 1f);
		//draw root container
		root.draw(mouseX, mouseY);
	}

	@Override
	public void post(String name, Element e, Object... args) {
		root.onEvent(name, args, e);
		onEvent(name, e, args);
	}
	
	public void onEvent(String name, Element e, Object... args){}
}
