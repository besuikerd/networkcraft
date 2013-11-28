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
	
	protected Box root;
	
	
	private int unknown_field_from_GuiScreen;
	private long lastMouseEvent;
	private int eventButton;

	public GuiTest() {
		
		root = new Box(200, 200, 200, 200);
		root.add(new ElementButton(20, 20, 120, 20, "TestButton"));
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
		root.draw(null, root.x, root.y, mouseX, mouseY);
	}
	
	@Override
	protected void keyTyped(char par1, int code) {
		if(code == mc.gameSettings.keyBindInventory.keyCode){
			mc.thePlayer.closeScreen();
		}
	}
	
	@Override
	protected void mouseClicked(int x, int y, int eventButton) {
		NCLogger.debug("mouse clicked! (%d, %d, %d)", x, y, eventButton);
		root.mouseClicked(x, y, eventButton);
	}
	
	@Override
	protected void mouseMovedOrUp(int x, int y, int eventButton) {
		NCLogger.debug("mouse moved or up! (%d, %d, %d)", x, y, eventButton);
		root.mouseMovedOrUp(x, y, eventButton);
	}
	
	public void handleMouseInput()
    {
		
        int x = Mouse.getEventX() * this.width / this.mc.displayWidth;
        int y = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
        
        //delegate mouse input to root Box
        root.handleMouseInput(x, y);
        
        NCLogger.debug("handling mouse input... (%d, %d)", x, y);
        if (Minecraft.isRunningOnMac && k == 0 && (Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157))) {
            k = 1;
        }

        
        
        
        if (Mouse.getEventButtonState())
        {
            if (this.mc.gameSettings.touchscreen && this.unknown_field_from_GuiScreen++ > 0)
            {
                return;
            }

            this.eventButton = k;
            this.lastMouseEvent = Minecraft.getSystemTime();
            this.mouseClicked(i, j, this.eventButton);
        }
        else if (k != -1)
        {
            if (this.mc.gameSettings.touchscreen && --this.unknown_field_from_GuiScreen > 0)
            {
                return;
            }

            this.eventButton = -1;
            this.mouseMovedOrUp(i, j, k);
        }
        else if (this.eventButton != -1 && this.lastMouseEvent > 0L)
        {
            long l = Minecraft.getSystemTime() - this.lastMouseEvent;
            mouseClickMove(i, j, this.eventButton, l);
        }
    }
}
