package nl.besuikerd.networkcraft.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import nl.besuikerd.networkcraft.core.NCLogger;

import org.lwjgl.opengl.GL11;

public class ElementButton extends ElementLabel{
	
	private static final Tuple TEX_DISABLED = new Tuple(0, 46, 200, 20);
	private static final Tuple TEX_ENABLED = new Tuple(0, 66, 200, 20);
	private static final Tuple TEX_HOVER = new Tuple(0, 86, 200, 20);
	private static final Tuple TEX_ACTIVE = new Tuple(0, 106, 200, 20);
	
	public ElementButton(int x, int y, int width, int height, String text) {
		super(x, y, width, height, text);
	}

	private GuiButton button;

	@Override
	public void draw(ElementContainer b, int mouseX, int mouseY) {
		//NCLogger.debug("rendering: %s", this);
		FontRenderer fontRenderer = mc.fontRenderer;
        mc.getTextureManager().bindTexture(textures);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        
        Tuple tex = null;
        if(isEnabled()){
        	if(isLeftClicked()){
        		tex = TEX_ACTIVE;
        	} else if(isHovering()){
	        	tex = TEX_HOVER;
	        } else{
	        	tex = TEX_ENABLED;
	        }
        } else{
        	tex = TEX_DISABLED;
        }
        int wHalf = width >> 1;
        drawTexturedModalRect(absX(), absY(), tex.int1(), tex.int2(), wHalf, height);
        drawTexturedModalRect(absX() + wHalf, absY(), tex.int3() - wHalf, tex.int2(), wHalf, height);
        fontRenderer.drawStringWithShadow(text, absX() + ((width - fontRenderer.getStringWidth(text)) / 2), absY() + ((height - fontRenderer.FONT_HEIGHT) / 2), enabledFlag());
        
        if(isFocused()){
        	renderBorder(1, 0xffffffff);
        }
        //drawCenteredString(fontrenderer, text, absX + wHalf, (absY + (height - 8)) >> 1, enabledFlag());
	}
	
	@Override
	protected boolean onDoublePressed(ElementContainer parent, int x, int y, int which) {
		super.onDoublePressed(parent, x, y, which);
		NCLogger.debug("double pressed at: (%d,%d) by %d", x, y, which);
		return false;
	}
	
	@Override
	protected boolean onMove(ElementContainer parent, int x, int y, int which) {
		super.onMove(parent, x, y, which);
		if(which == BUTTON_RIGHT){
			this.x = x;
			this.y = y;
		}
		return true;
	}
	
	@Override
	protected boolean onPressed(ElementContainer parent, int x, int y, int which) {
		super.onPressed(parent, x, y, which);
		return true;
	}
}
