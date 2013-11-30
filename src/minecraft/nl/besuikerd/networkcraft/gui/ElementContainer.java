package nl.besuikerd.networkcraft.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.google.common.base.Function;

import net.minecraft.block.BlockHopper;
import net.minecraft.client.Minecraft;
import nl.besuikerd.networkcraft.core.NCLogger;
import nl.besuikerd.networkcraft.core.utils.FunctionalUtils;
import nl.besuikerd.networkcraft.core.utils.MathUtils;

public class ElementContainer extends Element{
	
	//function that checks wheter or not a button is down or not
	private static final Function<Integer, Boolean> functionIsButtonDown = new Function<Integer, Boolean>() {
		@Override
		public Boolean apply(Integer input) {
			return Mouse.isButtonDown(input);
		}
	};
	
	protected List<Element> elements;
	protected Layout layout;
	
	protected int bgColor = 0x0;
	
	//used to determine element movement
	private int lastOffsetX;
	private int lastOffsetY;
	private int lastButtonPressed;
	private Element lastClicked;
	
	protected boolean movementConsumedByChild;
	
	public ElementContainer(int x, int y, int width, int height) {
		super(x, y, width, height);
		elements = new ArrayList<Element>();
	}

	public void add(Element e){
		this.elements.add(e);
		e.dx = e.x + this.dx;
	}
	
	@Override
	public void draw(ElementContainer parent, int mouseX, int mouseY) {
		//NCLogger.debug("(%d,%d) abs:(%d,%d), dx:(%d,%d)", x, y, absX(), absY(), dx, dy);
		drawRect(absX(), absY(), absX() + width, absY() + height, bgColor);
		
		for(int i = elements.size() - 1; i >= 0 ; i--){
			Element e = elements.get(i);
			//increment relative coordinates
			e.dx = absX();
			e.dy = absY();
			
			//let layout move element to it's correct position
			if(layout != null){
				layout.layout(parent, e, mouseX, mouseY);
			}
			
			//render element
			e.draw(this, mouseX, mouseY);
		}
	}

	public void handleMouseInput(){
		int x = Mouse.getEventX() * this.width / this.mc.displayWidth;
        int y = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
        handleMouseInput(this, x, y);
	}
	
	@Override
	protected boolean handleMouseInput(ElementContainer parent, int x, int y) {
		super.handleMouseInput(this, x, y);
		movementConsumedByChild = false;
		
		//process last clicked Element first
		if(lastClicked != null){
			
			
			int moveX = x - (absX() + lastOffsetX);
			int moveY = y - (absY() + lastOffsetY);
			movementConsumedByChild = lastClicked.onMove(this, Math.min(Math.max(0, moveX), width - lastClicked.width), Math.min(Math.max(0, moveY), height - lastClicked.height), lastButtonPressed);
			
			if(lastClicked.handleMouseInput(this, x, y)){
				return true;
			}
		}
		
		
		for(int i = 0 ; i < elements.size() ; i++){
			Element e = elements.get(i);

			if(!e.equals(lastClicked) && e.handleMouseInput(this, x, y)){ //skip lastClicked Element. If child element consumes mouse input, return
				return true;
			} else{
				
				//NCLogger.debug("(x,y) = (%d,%d) absX = (%d, %d), absY() = (%d,%d) | (%d, %d, %d, %d)", x,y, e.absX(), e.absX() + e.width, e.absY(), e.absY() + e.height, absX(), absY(), width, height);
				
				if(MathUtils.inRange2D(x, y, e.absX(), e.absX() + e.width, e.absY(), e.absY() + e.height)){ //element is within range
					

					if(lastClicked == null){
						//check if buttons are pressed
						for(int buttonFlag : Element.BUTTONS){
							if(Mouse.isButtonDown(Element.mouseMap.get(buttonFlag))){
								if(!e.is(buttonFlag) && e.isHovering()){
									e.toggleOn(buttonFlag);
									lastClicked = e;
									lastButtonPressed = Element.mouseMap.get(buttonFlag);
									lastOffsetX = x - e.absX();
									lastOffsetY = y - e.absY();
									long oldTime = e.lastClicks.get(buttonFlag);
									//set last click time on the current time
									e.lastClicks.put(buttonFlag, System.currentTimeMillis());
									
									if(e.onPressed(this, x, y, lastButtonPressed)){
										return true;
									}
									
									//check if button is double pressed
									if(System.currentTimeMillis() - oldTime < Element.THRESHOLD_DOUBLE_PRESS){
										if(e.onDoublePressed(this, x, y, lastButtonPressed)){
											return true;
										}
									}
									
									break; //exit looping through buttons; only 1 button press is allowed at a time
								}
								
								
							}
						}
					}
					
					//check if mouse is not yet hovering above element
					//if(!FunctionalUtils.foldl(false, Element.BUTTONS, FunctionalUtils.functionAny(functionIsButtonDown)) && !e.isHovering()){
					if(lastClicked == null && !e.isHovering()){
						e.toggleOn(Element.HOVERING);
						e.onHover(this, x, y);
					}
				} else{
					e.toggleOff(Element.HOVERING);
				}

				for(int buttonFlag : Element.BUTTONS){
					
					if(Mouse.isButtonDown(Element.mouseMap.get(buttonFlag))){
						e.toggleOff(Element.HOVERING);
						
						if(lastClicked != null && lastClicked.equals(e)){
							
						}
						
					} else if(e.is(buttonFlag)){ //check if buttons are released
						e.toggleOff(buttonFlag);
						e.onReleased(this, x, y, buttonFlag);
						lastClicked = null;
						lastButtonPressed = -1;
					}
				}
			}
		}
		
		//do not consume event
		return false;
	}
	
	/**
	 * focus given Element to bring them to the top of the screen
	 * @param e
	 */
	protected void focus(Element e){
		int index = elements.indexOf(e);
		if(index != -1){
			elements.remove(index);
			elements.add(0, e);
		}
	}
	
	public boolean isFrontElement(Element e){
		return elements.size() > 0 && elements.get(0).equals(e); 
	}
	
	@Override
	protected boolean onMove(ElementContainer parent, int x, int y, int which) {
		super.onMove(parent, x, y, which);
		if(!movementConsumedByChild){
			this.x = x;
			this.y = y;
			return true;
		}
		return false;
	}
}
