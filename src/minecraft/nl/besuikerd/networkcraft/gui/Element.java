package nl.besuikerd.networkcraft.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import nl.besuikerd.networkcraft.core.utils.MathUtils;

public abstract class Element extends Gui{
	
	protected static final ResourceLocation textures = new ResourceLocation("networkcraft", "textures/gui/elements.png");
	
	public static final int FLAG_ENABLED = -6250336;
	public static final int FLAG_DISABLED = 16777120;
	
	protected Minecraft mc;
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	
	protected int dx;
	protected int dy;
	
	protected ElementState state;
	
	protected boolean enabled;
	
	public Element(int x, int y, int width, int height) {
		this.mc = Minecraft.getMinecraft();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		this.enabled = true;
		this.state = ElementState.NORMAL;
		dx = 0;
		dy = 0;
	}
	
	public abstract void draw(Box parent, int absX, int absY, int mouseX, int mouseY);
	
	protected int enabledFlag(){
		return enabled ? FLAG_ENABLED : FLAG_DISABLED;
	}
	
	protected int absX(){
		return x + dx;
	}
	
	protected int absY(){
		return y + dy;
	}

	@Override
	public String toString() {
		return String.format("%s@%d [x=%d, y=%d, width=%d, height=%d, dx=%d, dy=%d, state=%s, enabled=%b]", getClass().getName(), hashCode(), x, y, width, height, dx, dy, state, enabled);
	}
	
	/**
	 * callback when the element is clicked on
	 */
	protected void onClick(int x, int y){
		this.state = ElementState.ACTIVATED;
	}
	
	/**
	 * callback when the element is released
	 * @param x
	 * @param y
	 */
	protected void onRelease(int x, int y){
		this.state = inRange() ? ElementState.HOVERED : ElementState.NORMAL;
	}
	
	/**
	 * callback when the mouse hovers over this element
	 */
	protected void onHover(int x, int y){
		this.state = ElementState.HOVERED;
	}
	
	/**
	 * callback for custom mouse input handling.
	 */
	protected void handleMouseInput(int x, int y){
		if(this.state == ElementState.HOVERED && !inRange()){
			this.state = ElementState.NORMAL;
		}
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
