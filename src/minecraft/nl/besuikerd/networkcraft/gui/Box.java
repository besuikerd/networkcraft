package nl.besuikerd.networkcraft.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;

import net.minecraft.block.BlockHopper;
import net.minecraft.client.Minecraft;
import nl.besuikerd.networkcraft.core.utils.MathUtils;

public class Box extends Element{
	
	protected List<Element> elements;
	protected Layout layout;
	
	public Box(int x, int y, int width, int height) {
		super(x, y, width, height);
		elements = new ArrayList<Element>();
	}

	public void add(Element e){
		this.elements.add(e);
	}
	
	@Override
	public void draw(Box parent, int absX, int absY, int mouseX, int mouseY) {
		for(Element e : elements){
			//increment relative coordinates
			e.dx = e.x + this.dx;
			e.dy = e.y + this.dy;
			
			//let layout move element to it's correct position
			if(layout != null){
				layout.layout(parent, e, mouseX, mouseY);
			}
			
			//render element
			e.draw(this, e.dx + dx, e.dy + dy, mouseX, mouseY);
		}
	}

	public void handleMouseInput(){
		int x = Mouse.getEventX() * this.width / this.mc.displayWidth;
        int y = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
        handleMouseInput(x, y);
	}
	
	@Override
	protected void handleMouseInput(int x, int y) {
		super.handleMouseInput(x, y);
		
		for(Element e : elements){
			if(MathUtils.inRange2D(x, y, e.absX(), e.absX() + e.width, e.absY(), e.absY() + e.height)){ //element is within range
				
				if(!e.isHovering()){
					e.toggleOn(Element.HOVERING);
					e.onHover(x, y);
				}
				
				for(int buttonFlag : Element.BUTTONS){
					if(Mouse.isButtonDown(Element.mouseMap(buttonFlag)) &&  !e.is(buttonFlag)){
						e.toggleOn(buttonFlag);
						e.onPressed(x, y, buttonFlag);
					} else{
						
					}
				}
				
				if(!e.() && Mouse.isButtonDown(Element.BUTTON_LEFT)){
					e.toggleOn(Element.);
					e.onPressed(x, y);
				} else if(e.isActivated()){
					e.toggleOff(Element.ACTIVATED);
				}
				
				
			}
			e.handleMouseInput(x, y);
			
		}
	}
}
