package nl.besuikerd.core.gui.element;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import nl.besuikerd.core.BLogger;
import nl.besuikerd.core.gui.layout.DefaultLayout;
import nl.besuikerd.core.gui.layout.Layout;
import nl.besuikerd.core.gui.layout.LayoutDimension;
import nl.besuikerd.core.utils.MathUtils;

import org.lwjgl.input.Mouse;

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
	
	public ElementContainer(LayoutDimension width, LayoutDimension height){
		this(0, 0, 0, 0);
		this.widthDimension = width;
		this.heightDimension = height;
	}
	
	public ElementContainer(){
		this(LayoutDimension.WRAP_CONTENT, LayoutDimension.WRAP_CONTENT);
	}

	public ElementContainer add(Element e){
		e.index = elements.size();
		this.elements.add(e);
		e.dx = e.x + this.dx;
		return this;
	}
	
	public void clear(){
		elements.clear();
	}
	
	public int getElementCount(){
		return elements.size();
	}
	
	@Override
	public void draw(ElementContainer parent, int mouseX, int mouseY, ElementContainer root) {
		if(root == null){ //init as root if no root exists
			root = this;
		}
		
		super.draw(parent, mouseX, mouseY, root);
		
		//render last element to first element
		for(int i = elements.size() - 1;  i >= 0 ; i--){
			Element e = elements.get(i);
			
			e.dx = absX();
			e.dy = absY();
			//check if element will fit
			if(true || e.x + e.width <= this.width && e.y + e.height < this.height){
				//render element
				e.draw(this, mouseX, mouseY, root);
			}
		}
	}
	
	@Override
	public void dimension(ElementContainer parent, ElementContainer root) {
		if(root == null){ //init as root if no root exists
			root = this;
		}
		
		
		
		layout.init(this, root);
		
		//dimension elements
		for(int i = 0 ; i < elements.size() ; i++){
			Element e = elements.get(i);
			//increment relative coordinates
			e.dimension(this, root);
		}
		
		//lay out elements
		for(int i = 0 ; i < elements.size() ; i++){
			Element e = elements.get(i);
			layout.layout(this, e, i, root);
		}
		
		Dimension laidOutDimension = layout.getLaidOutDimension();
		
		if(widthDimension == LayoutDimension.WRAP_CONTENT){
			this.width = laidOutDimension.width + paddingRight;
		}
		
		if(heightDimension == LayoutDimension.WRAP_CONTENT){
			this.height = laidOutDimension.height + paddingBottom;
		}
		
		//align elements
		for(Element e : elements){
			if(e.getAlignment() != null){
				layout.align(e, this);
			}
		}
		
		super.dimension(parent, root);
	}
	
	@Override
	public boolean handleMouseInput(ElementContainer parent, ElementContainer root, int x, int y) {
		if(root == null){
			root = this;
		}
		
		super.handleMouseInput(this, root, x, y);
		movementConsumedByChild = false;
		
		//process last clicked Element first
		if(lastClicked != null){
			
			int moveX = x - (absX() + lastOffsetX);
			int moveY = y - (absY() + lastOffsetY);
			movementConsumedByChild = lastClicked.onMove(this, Math.min(Math.max(0, moveX), width - lastClicked.width), Math.min(Math.max(0, moveY), height - lastClicked.height), lastButtonPressed);
			
			if(lastClicked.handleMouseInput(this, root, x, y)){
				return true;
			}
		}
		
		
		for(int i = 0 ; i < elements.size() ; i++){
			Element e = elements.get(i);

			if(!e.equals(lastClicked) && e.handleMouseInput(this, root, x, y)){ //skip lastClicked Element. If child element consumes mouse input, return
				return true;
			} else{
				
				
				if(root.inRange(x, y) && MathUtils.inRange2D(x, y, e.absX(), e.absX() + e.width - 1, e.absY(), e.absY() + e.height - 1)){ //element is within range
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

									e.lastClicks.put(buttonFlag, System.currentTimeMillis()); //set last click time on the current time
									
									if(e.onPressed(this, x - e.absX(), y - e.absY(), lastButtonPressed)){
										return true;
									}
									
									//check if button is double pressed
									if(System.currentTimeMillis() - oldTime < Element.THRESHOLD_DOUBLE_PRESS){
										if(e.onDoublePressed(this, x - e.absX(), e.absY(), lastButtonPressed)){
											return true;
										}
									}
									
									break; //exit looping through buttons; only 1 button press is allowed at a time
								}
								
								
							}
						}
					}
					
					//check if mouse is not yet hovering above element
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
		return false;
	}
	
	@Override
	public void onScrolled(ElementContainer parent, int x, int y, int amount) {
		super.onScrolled(parent, x, y, amount);
		for(Element e : elements){
			if(MathUtils.inRange2D(x, y, e.absX(), e.absX() + e.width - 1, e.absY(), e.absY() + e.height - 1)){ //element is within range
				e.onScrolled(parent, x - e.absX(), y - e.absY(), amount);
			}
		}
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
	
	public ElementContainer padding(int top, int right, int bottom, int left){
		this.paddingTop = top;
		this.paddingRight = right;
		this.paddingBottom = bottom;
		this.paddingLeft = left;
		return this;
	}
	
	public ElementContainer paddingTop(int padding){
		this.paddingTop = padding;
		return this;
	}
	
	public ElementContainer paddingRight(int padding){
		this.paddingRight = padding;
		return this;
	}
	
	public ElementContainer paddingBottom(int padding){
		this.paddingBottom = padding;
		return this;
	}
	
	public ElementContainer paddingLeft(int padding){
		this.paddingLeft = padding;
		return this;
	}
	
	public ElementContainer layout(Layout layout) {
		this.layout = layout;
		return this;
	}
}
