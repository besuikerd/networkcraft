package nl.besuikerd.core.gui.element;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import nl.besuikerd.core.gui.layout.DefaultLayout;
import nl.besuikerd.core.gui.layout.Layout;
import nl.besuikerd.core.gui.layout.LayoutDimension;

public class ElementContainer extends Element{
	
	
	protected int paddingTop;
	protected int paddingRight;
	protected int paddingBottom;
	protected int paddingLeft;
	protected List<Element> elements;
	
	protected Layout layout;
	
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
		e.parent = this;
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
	public void draw(ElementRootContainer root, int mouseX, int mouseY) {
		
		super.draw(root, mouseX, mouseY);
		
		//render last element to first element
		for(int i = elements.size() - 1;  i >= 0 ; i--){
			Element e = elements.get(i);
			
			e.dx = absX();
			e.dy = absY();
			
			e.draw(root, mouseX, mouseY);
		}
	}
	
	@Override
	public void dimension(ElementRootContainer root) {
		
		layout.init(this, root);
		
		//dimension elements
		for(int i = 0 ; i < elements.size() ; i++){
			Element e = elements.get(i);
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
