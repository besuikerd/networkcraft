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
		this.width = fontRenderer.getStringWidth(text) + 12;
		this.height = fontRenderer.FONT_HEIGHT + 6;
	}

	private GuiButton button;

	@Override
	public void draw(ElementRootContainer root, int mouseX, int mouseY) {
		super.draw(root, mouseX, mouseY);
		styler.style(this);
	}
	
	@Override
	protected boolean onPressed(ElementRootContainer root, int x, int y, int which) {
		super.onPressed(root, x, y, which);
		BLogger.debug("onPressed (%d, %d, %d)", x, y, which);
		root.requestFocus(this);
		return true;
	}
	
	@Override
	protected void onReleased(ElementRootContainer root, int x, int y, int which) {
		BLogger.debug("onReleased: (%d,%d,%d)", x, y, which);
		root.releaseFocus(this); 
	}
	
	@Override
	protected void onHover(ElementRootContainer root, int x, int y) {
		super.onHover(root, x, y);
		BLogger.debug("onHover (%d, %d)", x, y);
	}
	
	@Override
	protected boolean onDoublePressed(ElementRootContainer root, int x, int y, int which) {
		BLogger.debug("doublePressed: (%d,%d)", x, y);
		return true;
	}
	
	@Override
	protected boolean onScrolled(ElementRootContainer root, int x, int y, int amount) {
		BLogger.debug("scrolled: (%d,%d): %d", x, y, amount);
		return super.onScrolled(root, x, y, amount);
	}
	
	@Override
	protected void onFocus(ElementRootContainer root) {
		BLogger.debug("focused");
		super.onFocus(root);
	}
	
	@Override
	protected boolean onReleaseFocus(ElementRootContainer root) {
		BLogger.debug("focus lost");
		return super.onReleaseFocus(root);
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
