package nl.besuikerd.core.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import nl.besuikerd.core.BLogger;
import nl.besuikerd.core.gui.element.ElementContainer;
import nl.besuikerd.core.gui.element.ElementStyledContainer;
import nl.besuikerd.core.gui.layout.LayoutDimension;
import nl.besuikerd.core.gui.layout.VerticalLayout;
import nl.besuikerd.core.inventory.ContainerBesu;
import nl.besuikerd.core.inventory.TileEntityInventory;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class GuiBase extends GuiContainer{
	
	protected TileEntityInventory inventory;
	protected EntityPlayer player;
	protected World world;
	protected ElementContainer root;
	
	public GuiBase(ContainerBesu container) {
		super(container);
		root = new ElementStyledContainer(LayoutDimension.WRAP_CONTENT, LayoutDimension.WRAP_CONTENT).padding(5);
		root.layout(new VerticalLayout());
		init();
		root.dimension(null, null);
		xSize = root.getWidth();
		ySize = root.getHeight();
	}

	private static final ResourceLocation bg = new ResourceLocation("textures/gui/demo_background.png");
	
	protected void bindTileEntity(TileEntityInventory entity, EntityPlayer player, World world){
		this.inventory = entity;
		this.player = player;
		this.world = world;
	}

	@Override
	public void initGui() {
		super.initGui();
	}
	
	/**
	 * Override this method to attach Elements to the {@link #root} container
	 */
	public void init(){}
	
	@Override
	public void handleKeyboardInput() {
		root.handleKeyboardInput();
		super.handleKeyboardInput();
	}
	
	@Override
	protected void keyTyped(char key, int code) {
		if(!root.keyTyped(key, code)){
			super.keyTyped(key, code);
			
			if(code == mc.gameSettings.keyBindInventory.keyCode){
				mc.thePlayer.closeScreen();
			}
		}
	}
	
	
	
	@Override
	public void handleMouseInput() {
		super.handleMouseInput();
		
        int x = Mouse.getEventX() * this.width / this.mc.displayWidth;
        int y = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
        
        //delegate mouse input to root container
        root.handleMouseInput(null, x, y);
        

        int wheel = 0;
		if((wheel = Mouse.getDWheel()) != 0){
	        //delegate scroll input to root container
			root.onScrolled(null, x, y, wheel);
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
		//dimension all elements in the root container
		root.dimension(null, null);
		
		//recalculate size of the root container
		xSize = root.getWidth();
		ySize = root.getHeight();

		//center the root container
		root.setX((width - root.getWidth()) / 2);
		root.setY((height - root.getHeight()) / 2);
		
		mc.getTextureManager().bindTexture(root.getTextures());//TODO move this to a proper location
		GL11.glColor4f(1f, 1f, 1f, 1f);
		//draw root container
		root.draw(null, mouseX, mouseY, root);
	}
}
