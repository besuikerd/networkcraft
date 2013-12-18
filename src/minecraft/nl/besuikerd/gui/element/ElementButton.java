package nl.besuikerd.gui.element;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import nl.besuikerd.core.BLogger;
import nl.besuikerd.core.utils.Tuple;

import org.lwjgl.opengl.GL11;

public class ElementButton extends Element{
	
	private static final Tuple TEX_DISABLED = new Tuple(0, 24, 200, 20);
	private static final Tuple TEX_ENABLED = new Tuple(0, 44, 200, 20);
	private static final Tuple TEX_HOVER = new Tuple(0, 64, 200, 20);
	private static final Tuple TEX_ACTIVE = new Tuple(0, 84, 200, 20);
	
	public static final int COLOR_ENABLED = 0xffffa0;
	public static final int COLOR_DISABLED = 0xa0a0a0;
	
	protected String text;
	
	public ElementButton(int x, int y, int width, int height, String text) {
		super(x, y, width, height);
	}

	private GuiButton button;

	@Override
	public void draw(ElementContainer parent, int mouseX, int mouseY) {
		//NCLogger.debug("rendering: %s", this);
		super.draw(parent, mouseX, mouseY);
        
        Tuple tex = null;
        if(isEnabled()){
        	if(isLeftClicked()){
        		drawButtonBackground(ButtonBackground.ACTIVATED);
        	} else if(isHovering()){
	        	drawButtonBackground(ButtonBackground.HOVERING);
	        } else{
	        	drawButtonBackground(ButtonBackground.NORMAL);
	        }
        } else{
        	drawButtonBackground(ButtonBackground.DISABLED);
        }
        fontRenderer.drawStringWithShadow(text, absX() + ((width - fontRenderer.getStringWidth(text)) / 2), absY() + ((height - fontRenderer.FONT_HEIGHT) / 2), isEnabled() ? COLOR_ENABLED : COLOR_DISABLED);
        //drawCenteredString(fontrenderer, text, absX + wHalf, (absY + (height - 8)) >> 1, enabledFlag());
	}
	
	@Override
	protected boolean onDoublePressed(ElementContainer parent, int x, int y, int which) {
		super.onDoublePressed(parent, x, y, which);
		BLogger.debug("double pressed at: (%d,%d) by %d", x, y, which);
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
	
	private void drawButtonBackground(ButtonBackground bg){
		drawBackgroundFromTextures(bg.bg, bg.edgeTop, bg.edgeRight, bg.edgeBottom, bg.edgeLeft, bg.cornerTL, bg.cornerTR, bg.cornerBL, bg.cornerBR);
	}
	
	enum ButtonBackground{
		DISABLED(new Tuple(1, 25, 198, 18), new Tuple(1, 24, 198, 1), new Tuple(199, 25, 1, 18), new Tuple(1, 43, 198, 1), new Tuple(0, 25, 1, 18), new Tuple(0, 24, 1, 1), new Tuple(199, 24, 1, 1), new Tuple(199, 43, 1, 1), new Tuple(0, 43, 1, 1)),
		NORMAL(new Tuple(2, 46, 195, 15), new Tuple(2, 44, 196, 2), new Tuple(198, 46, 2, 15), new Tuple(2, 61, 196, 3), new Tuple(0, 46, 2, 15), new Tuple(0, 44, 2, 2), new Tuple(198, 44, 2, 2), new Tuple(0, 61, 2, 3), new Tuple(198, 61, 2, 3)),
		HOVERING(new Tuple(2, 66, 195, 15), new Tuple(2, 64, 196, 2), new Tuple(198, 66, 2, 15), new Tuple(2, 81, 196, 3), new Tuple(0, 66, 2, 15), new Tuple(0, 64, 2, 2), new Tuple(198, 64, 2, 2), new Tuple(0, 81, 2, 3), new Tuple(198, 81, 2, 3)),
		ACTIVATED(new Tuple(2, 87, 195, 15), new Tuple(2, 84, 196, 3), new Tuple(198, 87, 2, 15), new Tuple(2, 102, 196, 2), new Tuple(0, 87, 2, 15), new Tuple(0, 84, 2, 3), new Tuple(198, 84, 2, 3), new Tuple(0, 102, 2, 2), new Tuple(198, 102, 2, 2));
		
		private Tuple bg;
		private Tuple edgeTop;
		private Tuple edgeRight;
		private Tuple edgeBottom;
		private Tuple edgeLeft;
		private Tuple cornerTL;
		private Tuple cornerTR;
		private Tuple cornerBL;
		private Tuple cornerBR;
		
		private ButtonBackground(Tuple bg, Tuple edgeTop, Tuple edgeRight,
				Tuple edgeBottom, Tuple edgeLeft, Tuple cornerTL,
				Tuple cornerTR, Tuple cornerBL, Tuple cornerBR) {
			this.bg = bg;
			this.edgeTop = edgeTop;
			this.edgeRight = edgeRight;
			this.edgeBottom = edgeBottom;
			this.edgeLeft = edgeLeft;
			this.cornerTL = cornerTL;
			this.cornerTR = cornerTR;
			this.cornerBL = cornerBL;
			this.cornerBR = cornerBR;
		}
	}
}
