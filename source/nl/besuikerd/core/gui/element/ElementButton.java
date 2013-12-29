package nl.besuikerd.core.gui.element;

import net.minecraft.client.gui.GuiButton;
import nl.besuikerd.core.BLogger;
import nl.besuikerd.core.gui.texture.ElementState;
import nl.besuikerd.core.gui.texture.ElementStylerText;
import nl.besuikerd.core.gui.texture.IElementStyler;
import nl.besuikerd.core.gui.texture.IStateFulBackground;
import nl.besuikerd.core.gui.texture.StateFulBackground;
import nl.besuikerd.core.utils.Tuple;

public class ElementButton extends ElementStatefulBackground{
	
	private static final Tuple TEX_DISABLED = new Tuple(0, 24, 200, 20);
	private static final Tuple TEX_ENABLED = new Tuple(0, 44, 200, 20);
	private static final Tuple TEX_HOVER = new Tuple(0, 64, 200, 20);
	private static final Tuple TEX_ACTIVE = new Tuple(0, 84, 200, 20);
	
	protected IElementStyler styler;
	
	public ElementButton(int x, int y, int width, int height, IStateFulBackground<ElementState> bg, IElementStyler styler) {
		super(bg, x, y, width, height);
		this.styler = styler;
	}
	
	public ElementButton(int width, int height, IStateFulBackground<ElementState> bg, IElementStyler styler) {
		this(0, 0, width, height, bg, styler);
	}
	
	
	public ElementButton(int x, int y, int width, int height, IElementStyler styler) {
		this(x, y, width, height, StateFulBackground.BUTTON, styler);
	}
	
	public ElementButton(int x, int y, int width, int height, String text) {
		this(x, y, width, height, new ElementStylerText(text));
	}
	
	public ElementButton(int width, int height, String text) {
		this(0, 0, width, height, text);
	}
	
	public ElementButton(String text){
		this(0, 0, 0, 0, text);
		this.width = fontRenderer.getStringWidth(text) + 2;
		this.height = fontRenderer.FONT_HEIGHT + 2;
	}

	private GuiButton button;

	@Override
	public void draw(ElementContainer parent, int mouseX, int mouseY, ElementContainer root) {
		super.draw(parent, mouseX, mouseY, root);
		styler.style(this);
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
	
	public ElementButton styler(IElementStyler styler){
		this.styler = styler;
		return this;
	}
	
	public ElementButton background(IStateFulBackground<ElementState> background){
		this.statefulBackground = background;
		return this;
	}
}
