package nl.besuikerd.networkcraft.gui.element;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import nl.besuikerd.networkcraft.core.NCLogger;
import nl.besuikerd.networkcraft.core.packet.IDataProvider;
import nl.besuikerd.networkcraft.core.utils.MathUtils;
import nl.besuikerd.networkcraft.core.utils.Tuple;

import org.lwjgl.opengl.GL11;

public abstract class Element extends Gui implements IDataProvider{
	
	protected ResourceLocation textures = new ResourceLocation("networkcraft", "textures/gui/elements.png");
	
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
	 * should the dimensions of this element be saved?
	 */
	protected boolean saveDimensions = false;
	
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
	
	protected Minecraft mc;
	protected FontRenderer fontRenderer;
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	
	protected int dx;
	protected int dy;
	
	/**
	 * index assigned by container while adding it to a container
	 */
	protected int index;
	
	protected int state;
	
	public Element(int x, int y, int width, int height) {
		this.mc = Minecraft.getMinecraft();
		this.fontRenderer = mc.fontRenderer;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		this.state = ENABLED;
		
		dx = 0;
		dy = 0;
	}
	
	public Element(int width, int height){
		this(0, 0, width, height);
	}
	
	public void draw(ElementContainer parent, int mouseX, int mouseY){
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		if(textures != null){
			mc.getTextureManager().bindTexture(textures);
		}
	};
	
	public int absX(){
		return x + dx;
	}
	
	public int absY(){
		return y + dy;
	}
	
	/**
	 * callback when the element is clicked on
	 */
	protected boolean onPressed(ElementContainer parent, int x, int y, int which){
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
	
	@Override
	public String toString() {
		return String.format("%s[x=%d, y=%d, width=%d, height=%d, dx=%d, y=%d]", getClass().getCanonicalName(), x, y, width, height, dx, dy);
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
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	protected void renderBorder(int thickness, int color){
		//top border
		drawRectangle(thickness, 0, width - thickness, thickness, color);
		//bottom border
		drawRectangle(thickness, height - thickness, width - thickness, height, color);
		//left border
		drawRectangle(0, 0, thickness, height, color);
		//right border
		drawRectangle(width - thickness, 0, width , height, color);
	}
	
	public void drawTexturedModalRect(int xOffset, int yOffset, int u, int v, int width, int height){
		super.drawTexturedModalRect(absX() + xOffset, absY() + yOffset, u, v, width, height);
	}
	
	
	public void drawRectangle(int xOffset, int yOffset, int width, int height, int color){
		int xLeft = absX() + xOffset;
		int yLeft = absY() + yOffset;
		Gui.drawRect(xLeft, yLeft, xLeft + width, yLeft + height, color);
	}
	
	protected void drawBorderFromTextures(Tuple edgeTop, Tuple edgeRight, Tuple edgeBottom, Tuple edgeLeft, Tuple cornerTL, Tuple cornerTR, Tuple cornerBL, Tuple cornerBR){
		
		//draw top edge	
		int toDraw = width - (cornerTL.int3() + cornerTR.int3());
		while(toDraw > 0){
			//actual width being drawn this iteration
			int drawWidth = toDraw < edgeTop.int3() ? toDraw : edgeTop.int3();
			drawTexturedModalRect(width - (toDraw + cornerTR.int3()), 0, edgeTop.int1(), edgeTop.int2(), drawWidth, edgeTop.int4());
			toDraw -= drawWidth;
		}
		
		//draw bottom edge
		toDraw = width - (cornerBL.int3() + cornerBR.int3());
		while(toDraw > 0){
			//actual width being drawn this iteration
			int drawWidth = toDraw < edgeBottom.int3() ? toDraw : edgeBottom.int3();			
			drawTexturedModalRect(width - (toDraw + cornerBR.int3()), height - (edgeBottom.int4()), edgeBottom.int1(), edgeBottom.int2(), drawWidth, edgeBottom.int4());
			toDraw -= drawWidth;
		}
		
		//draw left edge
		toDraw = height - (cornerTL.int4() + cornerBL.int4());
		while(toDraw > 0){
			//actual height being drawn this iteration
			int drawHeight = toDraw < edgeLeft.int4() ? toDraw : edgeLeft.int4();
			drawTexturedModalRect(0, height - (toDraw + cornerBL.int4()), edgeLeft.int1(), edgeLeft.int2(), edgeLeft.int3(), drawHeight);
			toDraw -= drawHeight;
		}
		
		//draw right edge
		toDraw = height - (cornerTR.int4() + cornerBR.int4());
		while(toDraw > 0){
			//actual height being drawn this iteration
			int drawHeight = toDraw < edgeRight.int4() ? toDraw : edgeRight.int4();
			drawTexturedModalRect(width - edgeRight.int3(), height - (toDraw + cornerBR.int4()), edgeRight.int1(), edgeRight.int2(), edgeRight.int3(), drawHeight);
			toDraw -= drawHeight;
		}
		
		//draw top left corner
		drawTexturedModalRect(0, 0, cornerTL.int1(), cornerTL.int2(), cornerTL.int3(), cornerTL.int4());
		
		//draw top right corner
		drawTexturedModalRect(width - cornerTR.int3(), 0, cornerTR.int1(), cornerTR.int2(), cornerTR.int3(), cornerTR.int4());
		
		//draw bottom left corner
		drawTexturedModalRect(0, height - cornerBL.int4(), cornerBL.int1(), cornerBL.int2(), cornerBL.int3(), cornerBL.int4());
		
		//draw bottom right corner
		drawTexturedModalRect(width - cornerBR.int3(), height - cornerBR.int4(), cornerBR.int1(), cornerBR.int2(), cornerBR.int3(), cornerBR.int4());
	}
	
	protected void drawBackgroundFromTexture(Tuple bg){
		Tuple nullTuple = new Tuple(0,0,0,0);
		drawBackgroundFromTextures(bg, nullTuple, nullTuple, nullTuple, nullTuple, nullTuple, nullTuple, nullTuple, nullTuple);
	}
	
	protected void drawBackgroundFromTextures(Tuple bg, Tuple edgeTop, Tuple edgeRight, Tuple edgeBottom, Tuple edgeLeft, Tuple cornerTL, Tuple cornerTR, Tuple cornerBL, Tuple cornerBR){
		
		//height to render
		int toDrawY = height - (edgeTop.int4() + edgeBottom.int4());
		//draw a horizontal row
		while(toDrawY > 0){
			//actual height being drawn this iteration
			int drawHeight = toDrawY < bg.int4() ? toDrawY : bg.int4();
			int toDrawX = width - (edgeLeft.int3() + edgeRight.int3());
			while(toDrawX > 0){
				//actual width being drawn this iteration
				int drawWidth = toDrawX < bg.int3() ? toDrawX : bg.int3();
				drawTexturedModalRect(width - (toDrawX + edgeRight.int3()), height - (toDrawY + edgeBottom.int4()), bg.int1(), bg.int2(), drawWidth, drawHeight);
				toDrawX -= drawWidth;
			}
			toDrawY -= drawHeight; 
		}
		drawBorderFromTextures(edgeTop, edgeRight, edgeBottom, edgeLeft, cornerTL, cornerTR, cornerBL, cornerBR);
	}
	
	@Override
	public void readData(DataInputStream dis) throws IOException {
		if(saveDimensions){
			this.x = dis.readInt();
			this.y = dis.readInt();
			this.width = dis.readInt();
			this.height = dis.readInt();
		}
	}
	
	@Override
	public void writeData(DataOutputStream dos) throws IOException {
		if(saveDimensions){
			dos.writeInt(x);
			dos.writeInt(y);
			dos.writeInt(width);
			dos.writeInt(height);
		}
	}
}
