package nl.besuikerd.networkcraft.gui.element;

import java.util.ArrayList;
import java.util.List;

import nl.besuikerd.networkcraft.core.NCLogger;
import nl.besuikerd.networkcraft.core.utils.BitUtils;
import nl.besuikerd.networkcraft.core.utils.MathUtils;
import nl.besuikerd.networkcraft.core.utils.Tuple;
import nl.besuikerd.networkcraft.gui.layout.DefaultLayout;
import nl.besuikerd.networkcraft.gui.layout.Layout;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.google.common.base.Function;

public class ElementContainer extends Element{
	
	
	protected int paddingTop;
	protected int paddingRight;
	protected int paddingBottom;
	protected int paddingLeft;
	
	//function that checks wheter or not a button is down or not
	private static final Function<Integer, Boolean> functionIsButtonDown = new Function<Integer, Boolean>() {
		@Override
		public Boolean apply(Integer input) {
			return Mouse.isButtonDown(input);
		}
	};
	
	protected List<Element> elements;
	
	protected Layout layout;
	
	//used to determine element movement
	private int lastOffsetX;
	private int lastOffsetY;
	private int lastButtonPressed;
	private Element lastClicked;
	
	protected boolean movementConsumedByChild;
	
	public ElementContainer(int x, int y, int width, int height) {
		super(x, y, width, height);
		this.elements = new ArrayList<Element>();
		this.layout = new DefaultLayout();
	}
	
	public ElementContainer(int width, int height){
		this(0, 0, width, height);
	}

	public void add(Element e){
		e.index = elements.size();
		this.elements.add(e);
		e.dx = e.x + this.dx;
		
	}
	
	@Override
	public void draw(ElementContainer parent, int mouseX, int mouseY) {
		super.draw(parent, mouseX, mouseY);
		
		layout.init(this, mouseX, mouseY);
	
		int renderLimit = -1;
		
		for(int i = 0 ; i < elements.size() ; i++){
			Element e = elements.get(i);
			//increment relative coordinates
			e.dx = absX();
			e.dy = absY();
			
			//let layout move element to it's correct position
			if(layout.layout(this, e, i, mouseX, mouseY)){
				renderLimit++;
			}
		}
		
		for(int i = renderLimit; i >= 0 ; i--){
			Element e = elements.get(i);
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
	public boolean handleMouseInput(ElementContainer parent, int x, int y) {
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
				
				if(MathUtils.inRange2D(x, y, e.absX(), e.absX() + e.width - 1, e.absY(), e.absY() + e.height - 1)){ //element is within range
					

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
	
	public boolean isFrontElement(Element e){
		return elements.size() > 0 && elements.get(0).equals(e); 
	}
	
	@Override
	protected boolean onMove(ElementContainer parent, int x, int y, int which) {
		super.onMove(parent, x, y, which);
		/*
		if(!movementConsumedByChild){
			this.x = x;
			this.y = y;
			return true;
		}
		*/
		return false;
	}
	
	public int getPaddingTop() {
		return paddingTop;
	}
	
	public int getPaddingRight() {
		return paddingRight;
	}
	
	public int getPaddingBottom() {
		return paddingBottom;
	}
	
	public int getPaddingLeft() {
		return paddingLeft;
	}
	
	public ElementContainer padding(int padding){
		this.paddingTop = padding;
		this.paddingRight = padding;
		this.paddingBottom = padding;
		this.paddingLeft = padding;
		return this;
	}
	
	public void setLayout(Layout layout) {
		this.layout = layout;
	}
}
