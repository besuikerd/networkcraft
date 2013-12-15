package nl.besuikerd.networkcraft.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import nl.besuikerd.networkcraft.core.NCLogger;
import nl.besuikerd.networkcraft.core.inventory.NCContainer;
import nl.besuikerd.networkcraft.core.inventory.TileEntityInventory;
import nl.besuikerd.networkcraft.gui.element.ElementContainer;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class GuiBase extends GuiContainer{
	
	protected TileEntityInventory inventory;
	protected EntityPlayer player;
	protected World world;
	
	public GuiBase(NCContainer container) {
		super(container);
		root = new ElementContainer(Display.getWidth(), Display.getHeight());
	}

	private static final ResourceLocation bg = new ResourceLocation("textures/gui/demo_background.png");
	
	protected ElementContainer root;
	
	protected void bindTileEntity(TileEntityInventory entity, EntityPlayer player, World world){
		this.inventory = entity;
		this.player = player;
		this.world = world;
	}

	@Override
	public void initGui() {
		super.initGui();
		init();
		guiLeft = root.absX();
		guiTop = root.absY();
	}
	
	public void init(){}
	
	@Override
	protected void keyTyped(char par1, int code) {
		super.keyTyped(par1, code);
		
		if(code == mc.gameSettings.keyBindInventory.keyCode){
			mc.thePlayer.closeScreen();
		}
	}
	
	@Override
	public void handleMouseInput() {
		super.handleMouseInput();
		
        int x = Mouse.getEventX() * this.width / this.mc.displayWidth;
        int y = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
        
        //delegate mouse input to root Box
        root.handleMouseInput(null, x, y);
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
		root.draw(null, mouseX, mouseY);
	}
}
