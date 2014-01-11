package com.besuikerd.core.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.besuikerd.core.BLogger;
import com.besuikerd.core.gui.element.Element;
import com.besuikerd.core.gui.element.ElementContainer;
import com.besuikerd.core.gui.element.ElementRootContainer;
import com.besuikerd.core.gui.event.EventHandler;
import com.besuikerd.core.gui.event.IEventHandler;
import com.besuikerd.core.gui.layout.VerticalLayout;
import com.besuikerd.core.inventory.ContainerBesu;
import com.besuikerd.core.utils.profiling.BesuProfiler;

public class GuiBase extends GuiContainer implements IEventHandler{
	
	protected ElementRootContainer root;
	protected EventHandler eventHandler;
	
	public GuiBase(ContainerBesu container) {
		super(container);
		this.root = new ElementRootContainer();
		this.eventHandler = new EventHandler(this);
		root.setEventHandler(this);
		root.layout(new VerticalLayout())
		.padding(5);
		init();
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
	public void initGui() {
		
		root.update();
		
		//dimension all elements in the root container
		root.dimension();
		
		
		//recalculate size of the root container
		xSize = root.getWidth();
		ySize = root.getHeight();

		//center the root container
		root.x((width - root.getWidth()) / 2);
		root.y((height - root.getHeight()) / 2);
		
		super.initGui();
		return;
	}
	
	@Override
	public void drawScreen(int par1, int par2, float par3) {
		//update all elements before rendering
		root.update();
		
		//dimension all elements in the root container
		root.dimension();
		
		//recalculate size of the root container
		xSize = root.getWidth();
		ySize = root.getHeight();

		//center the root container
		root.x((width - root.getWidth()) / 2);
		root.y((height - root.getHeight()) / 2);
		
		this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
		super.drawScreen(par1, par2, par3);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY) {
		//draw root container
		root.draw(mouseX, mouseY);
		
		return;
	}

	@Override
	public void post(String name, Element e, Object... args) {
		root.onEvent(name, args, e);
		eventHandler.post(name, e, args);
		onEvent(name, e, args);
	}
	
	public void onEvent(String name, Element e, Object... args){
		
	}
}
