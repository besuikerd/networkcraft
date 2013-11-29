package nl.besuikerd.networkcraft.gui;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
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
	public static final int[] BUTTONS = new int[]{LEFT_CLICKED, RIGHT_CLICKED, MIDDLE_CLICKED};
	
	public static final Map<Integer, Integer> mouseMap = Collections.unmodifiableMap(new HashMap<Integer, Integer>(){{
		put(LEFT_CLICKED, BUTTON_LEFT);
		put(RIGHT_CLICKED, BUTTON_RIGHT);
		put(MIDDLE_CLICKED, BUTTON_MIDDLE);
	}});
	
	public static final Map<Integer, Long> lastClicks = new HashMap<Integer, Long>(){{
		put(BUTTON_LEFT, 0l);
		put(BUTTON_RIGHT, 0l);
		put(BUTTON_MIDDLE, 0l);
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
	
	public abstract void draw(Box parent, int mouseX, int mouseY);
	
	
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
	protected void onPressed(int x, int y, int which){
		
		toggleOn(which);
	}
	
	/**
	 * callback when the element is released
	 * @param x
	 * @param y
	 */
	protected void onReleased(int x, int y, int which){
		toggleOff(which);
	}
	
	/**
	 * callback when the mouse hovers over this element
	 */
	protected void onHover(int x, int y){
		toggleOn(HOVERING);
	}
	
	/**
	 * callback for custom mouse input handling. is called even when the mouse if not in range of the Element
	 */
	protected void handleMouseInput(int x, int y){
		if(isHovering() && !inRange()){
			toggleOff(HOVERING);
		}
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
	
	protected void toggle(int s, boolean on){
		this.state = on ? state | s : ((Integer.MAX_VALUE - s) & state);
	}
	
	protected void toggleOff(int s){
		toggle(s, false);
	}
	
	protected void toggleOn(int s){
		toggle(s, true);
	}
	
	/**
	 * callback when this element has been clicked on and the mouse is moved
	 */
	protected void onMove(){
	}
	
	protected boolean inRange(){
		return MathUtils.inRange2D(x, y, this.x, this.x + this.width, this.y, this.y + this.height);
	}
}
