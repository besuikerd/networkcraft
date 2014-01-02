package nl.besuikerd.core.gui.element;

import static nl.besuikerd.core.utils.TupleUtils.nullTuple;
import static nl.besuikerd.core.utils.TupleUtils.xDiff;
import static nl.besuikerd.core.utils.TupleUtils.yDiff;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import nl.besuikerd.core.gui.layout.Alignment;
import nl.besuikerd.core.gui.layout.LayoutDimension;
import nl.besuikerd.core.gui.texture.ElementState;
import nl.besuikerd.core.gui.texture.IStateFulBackground;
import nl.besuikerd.core.gui.texture.ITexture;
import nl.besuikerd.core.gui.texture.ITexturedBackground;
import nl.besuikerd.core.packet.IProcessData;
import nl.besuikerd.core.utils.MathUtils;
import nl.besuikerd.core.utils.Tuple;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

public abstract class Element extends Gui implements IProcessData{
	
	protected ResourceLocation textures = new ResourceLocation("networkcraft", "textures/gui/elements.png");
	
	public static final int ENABLED = 1;
	public static final int HOVERING = 2;
	public static final int LEFT_CLICKED = 4;
	public static final int RIGHT_CLICKED = 8;
	public static final int MIDDLE_CLICKED = 16;
	public static final int FOCUSED = 32;
	
	
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
	
	/**
	 * parent container
	 */
	protected ElementContainer parent;
	
	protected LayoutDimension widthDimension;
	protected LayoutDimension heightDimension;
	
	protected Alignment alignment;
	
	/**
	 * x offset the parent container has
	 */
	protected int dx;
	
	/**
	 * y offset the parent container has
	 */
	protected int dy;
	
	/**
	 * index assigned by container while adding it to a container
	 */
	protected int index;
	
	/**
	 * state flags are stored in this variable
	 */
	protected int state;
	
	protected int xOffsetButtonPress;
	protected int yOffsetButtonPress;
	
	public Element(int x, int y, int width, int height) {
		this.mc = Minecraft.getMinecraft();
		this.fontRenderer = mc.fontRenderer;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		this.widthDimension = LayoutDimension.ABSOLUTE;
		this.heightDimension = LayoutDimension.ABSOLUTE;
		
		this.state = ENABLED;
		
		dx = 0;
		dy = 0;
	}
	
	public Element(int width, int height){
		this(0, 0, width, height);
	}
	
	public void draw(ElementRootContainer root, int mouseX, int mouseY){
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		if(textures != null){
			mc.getTextureManager().bindTexture(textures);
		}
	};
	
	/**
	 * callback before drawing the Element. Enables the repositioning of elements before actually drawing them
	 */
	public void dimension(ElementRootContainer root){
	}
	
	/**
	 * Absolute x coordinate in the screen
	 * @return Absolute x coordinate in the screen
	 */
	public int absX(){
		return x + dx;
	}
	
	/**
	 * Absolute y coordinate in the screen
	 * @return Absolute y coordinate in the screen
	 */
	public int absY(){
		return y + dy;
	}
	
	/**
	 * callback when the element is clicked on
	 */
	protected boolean onPressed(ElementRootContainer root, int x, int y, int which){
		return false;
	}
	
	
	/**
	 * callback when mouse scroll wheel is changed when hovering over this element
	 */
	protected boolean onScrolled(ElementRootContainer root, int x, int y, int amount){
		return false;
	}
	
	/**
	 * callback when the element is released
	 * @param x
	 * @param y
	 */
	protected void onReleased(ElementRootContainer root, int x, int y, int which){
	}
	
	/**
	 * callback when the mouse hovers over this element
	 */
	protected void onHover(ElementRootContainer root, int x, int y){
	}
	
	/**
	 * callback when the mouse clicks twice on this element
	 */
	protected boolean onDoublePressed(ElementRootContainer root, int x, int y, int which){
		return false;
	}
	
	/**
	 * callback when this element has been clicked on and the mouse is moved
	 */
	protected boolean onMove(ElementRootContainer root, int x, int y, int which){
		return false;
	}
	
	protected void onFocus(ElementRootContainer root){
		
	}
	
	/**
	 * callback before focus is being released
	 * @param root root container
	 * @return whether this element allows focus to be released
	 */
	protected boolean onReleaseFocus(ElementRootContainer root){
		return true;
	}

	/**
	 * 
	 * @param parent parent container
	 * @param root root container
	 * @param mouseX mouse x coordinate relative to the parent container
	 * @param mouseY mouse y coordinate relative to the parent container
	 * @return true to consume mouse input
	 */
	protected boolean handleMouseInput(ElementRootContainer root, int mouseX, int mouseY){
		boolean consumeMouseInput = false; //should mouse input be consumed?
		
		
		for(int buttonFlag : BUTTONS){
			if(Mouse.isButtonDown(mouseMap.get(buttonFlag))){
				if(is(buttonFlag) /*&& xOffsetButtonPress - mouseX != x && yOffsetButtonPress - mouseY != y*/){ //element is moved
					consumeMouseInput = onMove(root, x + mouseX - xOffsetButtonPress, y + mouseY - yOffsetButtonPress, buttonFlag);
				}
			} else if(is(buttonFlag)){
				toggleOff(buttonFlag);
				onReleased(root, mouseX, mouseY, buttonFlag);
			}
		}
		if(MathUtils.inRange2D(absX() + mouseX, root.absX(), root.absX() + root.width, absY() + mouseY, root.absY(), root.absY() + root.height) && MathUtils.inRange2D(mouseX, 0, width, mouseY, 0, height)){ //check if mouse touches the element
			
			boolean aButtonIsDown = false; //is a button pressed?
			for(int buttonFlag : BUTTONS){
				if(Mouse.isButtonDown(mouseMap.get(buttonFlag))){
					aButtonIsDown = true;
					if(!is(buttonFlag) && is(HOVERING)){
						toggleOn(buttonFlag);
						xOffsetButtonPress = mouseX;
						yOffsetButtonPress = mouseY;
						consumeMouseInput = onPressed(root, mouseX, mouseY, buttonFlag) || consumeMouseInput;
						
						//handle double clicks
						long oldTime = lastClicks.get(buttonFlag);
						long currentTime = System.currentTimeMillis();
						lastClicks.put(buttonFlag, currentTime);
						if(currentTime - oldTime < THRESHOLD_DOUBLE_PRESS){
							consumeMouseInput = onDoublePressed(root, mouseX, mouseY, buttonFlag) || consumeMouseInput;
						}
						
						break; //exit looping through buttons; only 1 button press is allowed at a time
					}
				}
			}
			
			//handle hovering
			if(!aButtonIsDown){
				toggleOn(HOVERING);
				onHover(root, mouseX, mouseY);
			}
			
			//handle scroll input
			if(root.scrollMovement != 0){
				consumeMouseInput = onScrolled(root, x, y, root.scrollMovement) || consumeMouseInput;
			}
			
		} else{
			toggleOff(HOVERING);
		}
		
		return consumeMouseInput;
	}
	
	public boolean is(int flag){
		return (state & flag) == flag;
	}
	
	public boolean isEnabled(){
		return is(ENABLED);
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
	
	public boolean isFocused(){
		return is(FOCUSED);
	}
	
	public Element enabled(boolean enabled){
		toggle(ENABLED, enabled);
		return this;
	}
	
	protected Element toggle(int s, boolean on){
		this.state = on ? state | s : ((Integer.MAX_VALUE - s) & state);
		return this;
	}
	
	protected Element toggleOff(int s){
		toggle(s, false);
		return this;
	}
	
	protected Element toggleOn(int s){
		toggle(s, true);
		return this;
	}
	
	protected boolean inRange(int x, int y){
		return MathUtils.inRange2D(x, absX(), absX() + width, y, absY(), absY() + height);
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
	
	public LayoutDimension getWidthDimension() {
		return widthDimension;
	}
	
	public LayoutDimension getHeightDimension() {
		return heightDimension;
	}
	
	public Alignment getAlignment() {
		return alignment;
	}
	
	public Element align(Alignment alignment){
		this.alignment = alignment;
		return this;
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
	
	protected void drawBorderFromTextures(Tuple edgeTop, Tuple edgeRight, Tuple edgeBottom, Tuple edgeLeft, Tuple cornerTL, Tuple cornerTR, Tuple cornerBR, Tuple cornerBL){
		
		//draw top edge	
		int toDraw = width - (xDiff(cornerTL) + xDiff(cornerTR));
		while(toDraw > 0){
			//actual width being drawn this iteration
			int drawWidth = toDraw < xDiff(edgeTop) ? toDraw : xDiff(edgeTop);
			drawTexturedModalRect(width - (toDraw + xDiff(cornerTR)), 0, edgeTop.int1(), edgeTop.int2(), drawWidth, yDiff(edgeTop));
			toDraw -= drawWidth;
		}
		
		//draw bottom edge
		toDraw = width - (xDiff(cornerBL) + xDiff(cornerBR));
		while(toDraw > 0){
			//actual width being drawn this iteration
			int drawWidth = toDraw < xDiff(edgeBottom) ? toDraw : xDiff(edgeBottom);			
			drawTexturedModalRect(width - (toDraw + xDiff(cornerBR)), height - (yDiff(edgeBottom)), edgeBottom.int1(), edgeBottom.int2(), drawWidth, yDiff(edgeBottom));
			toDraw -= drawWidth;
		}
		
		//draw left edge
		toDraw = height - (yDiff(cornerTL) + yDiff(cornerBL));
		while(toDraw > 0){
			//actual height being drawn this iteration
			int drawHeight = toDraw < yDiff(edgeLeft) ? toDraw : yDiff(edgeLeft);
			drawTexturedModalRect(0, height - (toDraw + yDiff(cornerBL)), edgeLeft.int1(), edgeLeft.int2(), xDiff(edgeLeft), drawHeight);
			toDraw -= drawHeight;
		}
		
		//draw right edge
		toDraw = height - (yDiff(cornerTR) + yDiff(cornerBR));
		while(toDraw > 0){
			//actual height being drawn this iteration
			int drawHeight = toDraw < yDiff(edgeRight) ? toDraw : yDiff(edgeRight);
			drawTexturedModalRect(width - xDiff(edgeRight), height - (toDraw + yDiff(cornerBR)), edgeRight.int1(), edgeRight.int2(), xDiff(edgeRight), drawHeight);
			toDraw -= drawHeight;
		}
		
		//draw top left corner
		drawTexturedModalRect(0, 0, cornerTL.int1(), cornerTL.int2(), xDiff(cornerTL), yDiff(cornerTL));
		
		//draw top right corner
		drawTexturedModalRect(width - xDiff(cornerTR), 0, cornerTR.int1(), cornerTR.int2(), xDiff(cornerTR), yDiff(cornerTR));
		
		//draw bottom left corner
		drawTexturedModalRect(0, height - yDiff(cornerBL), cornerBL.int1(), cornerBL.int2(), xDiff(cornerBL), yDiff(cornerBL));
		
		//draw bottom right corner
		drawTexturedModalRect(width - xDiff(cornerBR), height - yDiff(cornerBR), cornerBR.int1(), cornerBR.int2(), xDiff(cornerBR), yDiff(cornerBR));
	}
	
	protected void drawBackgroundFromTexture(Tuple bg){
		drawBackgroundFromTextures(bg, nullTuple, nullTuple, nullTuple, nullTuple, nullTuple, nullTuple, nullTuple, nullTuple);
	}
	
	protected void drawBackgroundFromTextures(Tuple bg, Tuple edgeTop, Tuple edgeRight, Tuple edgeBottom, Tuple edgeLeft, Tuple cornerTL, Tuple cornerTR, Tuple cornerBR, Tuple cornerBL){
		
		//height to render
		int toDrawY = height - (yDiff(edgeTop) + yDiff(edgeBottom));
		//draw a horizontal row
		while(toDrawY > 0){
			//actual height being drawn this iteration
			int drawHeight = toDrawY < yDiff(bg) ? toDrawY : yDiff(bg);
			int toDrawX = width - (xDiff(edgeLeft) + xDiff(edgeRight));
			while(toDrawX > 0){
				//actual width being drawn this iteration
				int drawWidth = toDrawX < xDiff(bg) ? toDrawX : xDiff(bg);
				drawTexturedModalRect(width - (toDrawX + xDiff(edgeRight)), height - (toDrawY + yDiff(edgeBottom)), bg.int1(), bg.int2(), drawWidth, drawHeight);
				toDrawX -= drawWidth;
			}
			toDrawY -= drawHeight; 
		}
		drawBorderFromTextures(edgeTop, edgeRight, edgeBottom, edgeLeft, cornerTL, cornerTR, cornerBR, cornerBL);
	}
	
	protected void drawBackgroundFromTextures(Tuple bg, Tuple edgeTop, Tuple edgeRight, Tuple edgeBottom, Tuple edgeLeft){
		drawBackgroundFromTextures(bg, edgeTop, edgeRight, edgeBottom, edgeLeft, nullTuple, nullTuple, nullTuple, nullTuple);
	}
	
	protected void drawBackgroundFromTextures(ITexturedBackground texture){
		drawBackgroundFromTextures(texture.background(), texture.edgeTop(), texture.edgeRight(), texture.edgeBottom(), texture.edgeLeft(), texture.cornerTL(), texture.cornerTR(), texture.cornerBR(), texture.cornerBL());
	}
	
	protected void drawStatefulBackgroundFromTextures(IStateFulBackground<ElementState> bg, ElementState state){
		drawBackgroundFromTextures(bg.backgroundForState(state));
	}
	
	protected void drawStatefulBackgroundFromTextures(IStateFulBackground<ElementState> bg){
		ElementState state = isEnabled() ? isLeftClicked() ? ElementState.ACTIVATED : isHovering() ? ElementState.HOVERING : ElementState.NORMAL : ElementState.DISABLED;
		drawStatefulBackgroundFromTextures(bg, state);
	}
	
	protected void drawTexture(ITexture texture, int x, int y){
		drawTexturedModalRect(x, y, texture.getTexture().int1(), texture.getTexture().int2(), xDiff(texture.getTexture()), yDiff(texture.getTexture()));
	}
	
	protected void drawTextureCentered(ITexture texture){
		drawTexture(texture, (width - xDiff(texture.getTexture())) / 2, (height - yDiff(texture.getTexture()) / 2));
	}
	
	
	@Override
	public void read(ByteArrayDataInput in) {
		if(saveDimensions){
			this.x = in.readInt();
			this.y = in.readInt();
			this.width = in.readInt();
			this.height = in.readInt();
		}
	}
	
	@Override
	public void write(ByteArrayDataOutput out) {
		if(saveDimensions){
			out.writeInt(x);
			out.writeInt(y);
			out.writeInt(width);
			out.writeInt(height);
		}
	}
	
	public FontRenderer getFontRenderer() {
		return fontRenderer;
	}
	
	public Minecraft getMinecraft() {
		return mc;
	}
	
	public ResourceLocation getTextures() {
		return textures;
	}
}
