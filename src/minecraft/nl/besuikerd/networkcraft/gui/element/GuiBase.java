package nl.besuikerd.networkcraft.gui.element;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import nl.besuikerd.networkcraft.gui.INCGui;
import nl.besuikerd.networkcraft.gui.layout.HoritzontalLayout;
import nl.besuikerd.networkcraft.gui.layout.VerticalLayout;

import org.lwjgl.input.Mouse;

public class GuiBase extends GuiScreen implements INCGui<TileEntity>{
	
	private static final ResourceLocation bg = new ResourceLocation("textures/gui/demo_background.png");
	
	protected ElementContainer root;
	
	private long lastMouseEvent;
	private int eventButton;

	public GuiBase() {
		
		root = new ElementContainer(5, 5, 400, 160);
		
		
		ElementContainer container = new ElementStyledContainer(0, 0, 180, 100);
		
		container.layout = new VerticalLayout(5, 5);
		
		for(int i = 0 ; i < 5; i++){
			container.add(new ElementButton(0, 0, 60 + i*5, 20 + i*5, String.format("TstBtn%d", i)));
		}
		
		ElementContainer container2 = new ElementStyledContainer(190, 0, 180, 100);
		container2.add(new ElementItemContainerArray(25, 5));
		
		root.add(container);
		root.add(container2);
		
		this.width = root.width;
		this.height = root.height;
	}
	
	@Override
	public Object onOpenend(TileEntity entity, EntityPlayer player, World w,
			int x, int y, int z) {
		return new GuiBase();
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
