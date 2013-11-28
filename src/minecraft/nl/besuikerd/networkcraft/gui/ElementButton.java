package nl.besuikerd.networkcraft.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import nl.besuikerd.networkcraft.core.NCLogger;

import org.lwjgl.opengl.GL11;

public class ElementButton extends ElementLabel{
	
	private static final Tuple TEX_DISABLED = new Tuple(0, 46, 200, 20);
	private static final Tuple TEX_ENABLED = new Tuple(0, 66, 200, 20);
	private static final Tuple TEX_HOVER = new Tuple(0, 86, 200, 20);
	
	public ElementButton(int x, int y, int width, int height, String text) {
		super(x, y, width, height, text);
	}

	private GuiButton button;

	@Override
	public void draw(Box b, int absX, int absY, int mouseX, int mouseY) {
		//NCLogger.debug("rendering: %s", this);
		FontRenderer fontRenderer = mc.fontRenderer;
        mc.getTextureManager().bindTexture(textures);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        
        Tuple tex = null;
        if(enabled && state != null){
	        switch(state){
	        case HOVERED:
	        	tex = TEX_HOVER;
	        	break;
        	default:
        		tex = TEX_ENABLED;
        		break;
	        }
        } else{
        	tex = TEX_DISABLED;
        }
        int wHalf = width >> 1;
        drawTexturedModalRect(absX, absY, tex.int1(), tex.int2(), wHalf, height);
        drawTexturedModalRect(absX + wHalf, absY, tex.int3() - wHalf, tex.int2(), wHalf, height);
        fontRenderer.drawStringWithShadow(text, absX + ((width - fontRenderer.getStringWidth(text)) / 2), absY + ((height - fontRenderer.FONT_HEIGHT) / 2), enabledFlag());
        
        //drawCenteredString(fontrenderer, text, absX + wHalf, (absY + (height - 8)) >> 1, enabledFlag());
	}
}
