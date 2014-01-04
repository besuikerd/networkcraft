package nl.besuikerd.core.gui.element;

import org.lwjgl.input.Mouse;

import nl.besuikerd.core.BLogger;
import nl.besuikerd.core.gui.event.IEventHandler;
import nl.besuikerd.core.gui.layout.LayoutDimension;
import nl.besuikerd.core.gui.texture.scalable.ScalableTexture;

public class ElementRootContainer extends ElementStyledContainer{
	protected Element focusedElement;
	protected int scrollMovement;
	protected IEventHandler eventHandler;
	
	public ElementRootContainer() {
		super(LayoutDimension.WRAP_CONTENT, LayoutDimension.WRAP_CONTENT, ScalableTexture.STYLED_CONTAINER);
	}
	
	public ElementRootContainer(int x, int y, int width, int height) {
		super(x, y, width, height);
	}
	
	public ElementRootContainer(int width, int height) {
		this(0, 0, width, height);
	}



	public boolean requestFocus(Element element){
		boolean canFocus = true;
		if(focusedElement != null){
			canFocus = focusedElement.onReleaseFocus(this);
		}
		if(canFocus){
			if(focusedElement != null){
				focusedElement.toggleOff(Element.FOCUSED);
			}
			this.focusedElement = element;
			element.onFocus(this);
			element.toggleOn(Element.FOCUSED);
		}
		return canFocus;
	}
	
	public void releaseFocus(Element element){
		if(focusedElement != null && focusedElement.equals(element)){
			focusedElement.onReleaseFocus(this);
			focusedElement.toggleOff(Element.FOCUSED);
			focusedElement = null;
		}
	}
	
	public Element getFocusedElement() {
		return focusedElement;
	}
	
	@Override
	public boolean handleMouseInput(ElementRootContainer root, int mouseX, int mouseY) {
		
		this.scrollMovement = Mouse.getDWheel();
		
		boolean consumeMouseInput = false;
		if(focusedElement != null){
			consumeMouseInput = focusedElement.handleMouseInput(this, mouseX - focusedElement.absX(), mouseY - focusedElement.absY());
		}
		
		if(!consumeMouseInput){
			super.handleMouseInput(root, mouseX - absX(), mouseY - absY());
		}
		return true; //root element always consumes mouse input
	}
	
	@Override
	public boolean handleKeyboardInput(ElementRootContainer root) {
		boolean consumeKeyboardInput = false;
		
		if(focusedElement != null){
			consumeKeyboardInput = focusedElement.handleKeyboardInput(root);
		}
		if(!consumeKeyboardInput){
			consumeKeyboardInput = super.handleKeyboardInput(root);
		}
		
		return consumeKeyboardInput;
	}
	
	public void setEventHandler(IEventHandler eventHandler) {
		this.eventHandler = eventHandler;
	}
	
	public IEventHandler getEventHandler() {
		return eventHandler;
	}
}
