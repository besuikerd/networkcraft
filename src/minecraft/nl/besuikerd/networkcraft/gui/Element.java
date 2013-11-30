package nl.besuikerd.networkcraft.gui;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import nl.besuikerd.networkcraft.core.NCLogger;
import nl.besuikerd.networkcraft.core.utils.BitUtils;
import nl.besuikerd.networkcraft.core.utils.MathUtils;

public abstract class Element extends Gui{
	
	protected static final ResourceLocation textures = new ResourceLocation("networkcraft", "textures/gui/elements.png");
	
	public static final int ENABLED = 1;
	public static final int HOVERING = 2;
	public static final int FOCUSED = 4;
	public static final int LEFT_CLICKED = 8;
	public static final int RIGHT_CLICKED = 16;
	public static final int MIDDLE_CLICKED = 32;
	
	
	public static final int BUTTON_LEFT = 0;
	public static final int BUTTON_RIGHT = 1;
	public static final int BUTTON_MIDDLE = 2;
	public static final Integer[] BUTTONS = new Integer[]{LEFT_CLICKED, RIGHT_CLICKED, MIDDLE_CLICKED};
	
	/**
	 * 200 ms delay for double presses
	 */
	public static final long THRESHOLD_DOUBLE_PRESS = 200l;
	
	public static final Map<Integer, Integer> mouseMap = Collections.unmodifiableMap(new HashMap<Integer, Integer>(){{
		put(LEFT_CLICKED, BUTTON_LEFT);
		put(RIGHT_CLICKED, BUTTON_RIGHT);
		put(MIDDLE_CLICKED, BUTTON_MIDDLE);
	}});
	
	protected Map<Integer, Long> lastClicks = new HashMap<Integer, Long>(){{
		put(LEFT_CLICKED, 0l);
		put(RIGHT_CLICKED, 0l);
		put(MIDDLE_CLICKED, 0l);
	}};
	
	public static final int FLAG_ENABLED = -6250336;
	public static final int FLAG_DISABLED = 16777120;
	
	protected Minecraft mc;
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	
	protected int dx;
	protected int dy;
	
	
	protected int state;
	
	public Element(int x, int y, int width, int height) {
		this.mc = Minecraft.getMinecraft();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		this.state = ENABLED;
		
		dx = 0;
		dy = 0;
	}
	
	public abstract void draw(ElementContainer parent, int mouseX, int mouseY);
	
	
	protected int enabledFlag(){
		//TODO review this, this was ripped from the source code, probably something to do with text color
		return isEnabled() ? FLAG_ENABLED : FLAG_DISABLED;
	}
	
	protected int absX(){
		return x + dx;
	}
	
	protected int absY(){
		return y + dy;
	}
	
	/**
	 * callback when the element is clicked on
	 */
	protected boolean onPressed(ElementContainer parent, int x, int y, int which){
		parent.focus(this);
		toggleOn(FOCUSED);
		return false;
	}
	
	/**
	 * callback when the element is released
	 * @param x
	 * @param y
	 */
	protected void onReleased(ElementContainer parent, int x, int y, int which){
	}
	
	/**
	 * callback when the mouse hovers over this element
	 */
	protected void onHover(ElementContainer parent, int x, int y){
	}
	
	/**
	 * callback when the mouse clicks twice on this element
	 */
	protected boolean onDoublePressed(ElementContainer parent, int x, int y, int which){
		return false;
	}
	
	/**
	 * callback when this element has been clicked on and the mouse is moved
	 */
	protected boolean onMove(ElementContainer parent, int x, int y, int which){
		return false;
	}
	
	/**
	 * callback for custom mouse input handling. is called even when the mouse if not in range of the Element
	 * @return if the Element should consume the mouse event
	 */
	protected boolean handleMouseInput(ElementContainer parent, int x, int y){
		if(isFocused() && !parent.isFrontElement(this)){
			toggleOff(FOCUSED);
		}
		return false;
	}
	
	public boolean is(int flag){
		return (state & flag) == flag;
	}
	
	public boolean isEnabled(){
		return is(ENABLED);
	}
	
	public boolean isFocused(){
		return is(FOCUSED);
	}
	
	public boolean isLeftClicked(){
		return is(LEFT_CLICKED);
	}
	
	public boolean isRightClicked(){
		return is(RIGHT_CLICKED);
	}
	
	public boolean isMiddleClicked(){
		return is(MIDDLE_CLICKED);
	}
	
	public boolean isHovering(){
		return is(HOVERING);
	}
	
	public void setEnabled(boolean enabled){
		toggle(ENABLED, enabled);
	}
	
	protected void toggle(int s, boolean on){
		this.state = on ? state | s : ((Integer.MAX_VALUE - s) & state);
	}
	
	protected void toggleOff(int s){
		toggle(s, false);
	}
	
	protected void toggleOn(int s){
		toggle(s, true);
	}
	
	protected boolean inRange(int x, int y){
		return MathUtils.inRange2D(x, y, this.x, this.x + this.width, this.y, this.y + this.height);
	}
	
	protected void renderBorder(int thickness, int color){
		//top border
		drawRect(absX() + thickness, absY(), absX() + width - thickness, absY() + thickness, color);
		//bottom border
		drawRect(absX() + thickness, absY() + height - thickness, absX() + width - thickness, absY() + height, color);
		//left border
		drawRect(absX(), absY(), absX() + thickness, absY() + height, color);
		//right border
		drawRect(absX() + width - thickness, absY(), absX() + width , absY() + height, color);
	}
}
