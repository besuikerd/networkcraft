package nl.besuikerd.networkcraft.gui;

import java.awt.Button;
import java.sql.NClob;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import nl.besuikerd.networkcraft.core.NCLogger;

public class GuiTest extends GuiScreen implements INCGui<TileEntity>{
	
	private static final ResourceLocation bg = new ResourceLocation("textures/gui/demo_background.png");
	
	protected ElementContainer root;
	
	
	private int unknown_field_from_GuiScreen;
	private long lastMouseEvent;
	private int eventButton;

	public GuiTest() {
		
		root = new ElementContainer(50, 50, 300, 200);
		root.bgColor = 0xffff0000;
		ElementContainer container = new ElementContainer(20, 20, 190, 105);
		container.bgColor = 0xff0000ff;
		root.add(container);
		
		
		container.add(new ElementButton(20, 20, 120, 20, "TestButton"));
		container.add(new ElementButton(20, 50, 120, 20, "TestButton2"));
		this.width = root.width;
		this.height = root.height;
	}
	
	@Override
	public Object onOpenend(TileEntity entity, EntityPlayer player, World w,
			int x, int y, int z) {
		return new GuiTest();
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float par3) {
		drawDefaultBackground();
		root.draw(null, mouseX, mouseY);
	}
	
	@Override
	protected void keyTyped(char par1, int code) {
		if(code == mc.gameSettings.keyBindInventory.keyCode){
			mc.thePlayer.closeScreen();
		}
	}
	
	@Override
	public void handleMouseInput() {
        int x = Mouse.getEventX() * this.width / this.mc.displayWidth;
        int y = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
        
        //delegate mouse input to root Box
        root.handleMouseInput(null, x, y);
    }
}
