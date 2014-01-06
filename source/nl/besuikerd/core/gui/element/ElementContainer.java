package nl.besuikerd.core.gui.element;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import nl.besuikerd.core.BLogger;
import nl.besuikerd.core.gui.layout.DefaultLayout;
import nl.besuikerd.core.gui.layout.Layout;
import nl.besuikerd.core.gui.layout.LayoutDimension;

public class ElementContainer extends Element{
	
	
	protected int paddingTop;
	protected int paddingRight;
	protected int paddingBottom;
	protected int paddingLeft;
	protected List<Element> elements;
	
	/**
	 * used to remove elements from this container. Removals are buffered to prevent ConcurrentModificationExceptions
	 */
	protected List<Element> pendingRemovals;
	
	/**
	 * used to add elements to this container. Additions are buffered to prevent ConcurrentModificationExceptions
	 */
	protected List<Element> pendingAdditions;
	
	protected Layout layout;
	
	public ElementContainer(int x, int y, int width, int height) {
		super(x, y, width, height);
		this.elements = new ArrayList<Element>();
		this.pendingRemovals = new ArrayList<Element>();
		this.pendingAdditions = new ArrayList<Element>();
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

	public ElementContainer add(Element... elements){
		for(Element e : elements){
			pendingAdditions.add(e);
		}
		return this;
	}
	
	public void clear(){
		elements.clear();
	}
	
	public ElementContainer remove(Element e){
		pendingRemovals.add(e);
		return this;
	}
	
	public ElementContainer remove(int index){
		pendingRemovals.add(elements.get(index));
		return this;
	}
	
	public int getElementCount(){
		return elements.size();
	}
	
	public int indexOf(Element e){
		return elements.indexOf(e);
	}
	
	public Element elementAt(int index){
		return elements.get(index);
	}
	
	@Override
	public void draw(ElementRootContainer root, int mouseX, int mouseY) {
		
		super.draw(root, mouseX, mouseY);
		
		//render last element to first element
		for(int i = elements.size() - 1;  i >= 0 ; i--){
			Element e = elements.get(i);
			e.draw(root, mouseX, mouseY);
			e.style();
		}
	}
	
	@Override
	public void dimension(ElementRootContainer root) {
		layout.init(this, root);
		
		//dimension elements
		for(int i = 0 ; i < elements.size() ; i++){
			Element e = elements.get(i);
			
			e.dx = absX();
			e.dy = absY();
			
			//increment relative coordinates
			e.dimension(root);
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
		
		super.dimension(root);
	}
	
	@Override
	public void update(ElementRootContainer root) {
		super.update(root);
		
		for(Element e : pendingRemovals){
			e.onRemoved(root);
			elements.remove(e);
		}
		
		for(Element e : pendingAdditions){			
			elements.add(e);
			e.index = elements.size();
			e.parent = this;
			e.onAdded(root);
		}
		
		pendingRemovals.clear();
		pendingAdditions.clear();
		
		for(Element e : elements){
			e.update(root);
		}
	}
	
	@Override
	public boolean handleMouseInput(ElementRootContainer root, int mouseX, int mouseY) {
		boolean consumeMouseInput = super.handleMouseInput(root, mouseX, mouseY);
		
		if(!consumeMouseInput){
			for(Element e : elements){
				consumeMouseInput = e.handleMouseInput(root, mouseX - e.x, mouseY - e.y) || consumeMouseInput;
			}
		}
		
		return consumeMouseInput;
	}
	
	@Override
	public boolean handleKeyboardInput(ElementRootContainer root) {
		boolean consumeKeyboardInput = super.handleKeyboardInput(root);
		
		if(!consumeKeyboardInput){
			for(Element e : elements){
				if((consumeKeyboardInput = e.handleKeyboardInput(root))){
					break;
				}
			}
		}
		
		return consumeKeyboardInput;
	}
	
	@Override
	public void onEvent(String name, Object[] args, ElementRootContainer root, Element e) {
		super.onEvent(name, args, root, e);
		for(Element el : elements){ //delegate events to children
			el.onEvent(name, args, root, e);
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
