package nl.besuikerd.networkcraft.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import net.minecraft.block.BlockHopper;
import net.minecraft.client.Minecraft;
import nl.besuikerd.networkcraft.core.NCLogger;
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
		e.dx = e.x + this.dx;
	}
	
	@Override
	public void draw(Box parent, int mouseX, int mouseY) {
		//NCLogger.debug("(%d,%d) abs:(%d,%d), dx:(%d,%d)", x, y, absX(), absY(), dx, dy);
		drawRect(absX(), absY(), absX() + width, absY() + height, 0xffff0000);
		
		for(Element e : elements){
			
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
        handleMouseInput(x, y);
	}
	
	@Override
	protected void handleMouseInput(int x, int y) {
		super.handleMouseInput(x, y);
		
		for(Element e : elements){
			NCLogger.debug("(x,y) = (%d,%d) absX = (%d, %d), absY() = (%d,%d) | (%d, %d, %d, %d)", x,y, e.absX(), e.absX() + e.width, e.absY(), e.absY() + e.height, absX(), absY(), width, height);
			if(MathUtils.inRange2D(x, y, e.absX(), e.absX() + e.width, e.absY(), e.absY() + e.height)){ //element is within range
				
				if(!e.isHovering()){
					e.toggleOn(Element.HOVERING);
					e.onHover(x, y);
				}
				
				for(int buttonFlag : Element.BUTTONS){
					if(Mouse.isButtonDown(Element.mouseMap.get(buttonFlag)) && !e.is(buttonFlag)){
						e.toggleOn(buttonFlag);
						e.onPressed(x, y, buttonFlag);
					}
				}
				
			} else{
				e.toggleOff(Element.HOVERING);
			}
			
			for(int buttonFlag : Element.BUTTONS){
				
				if(!Mouse.isButtonDown(Element.mouseMap.get(buttonFlag)) && e.is(buttonFlag)){
					e.toggleOff(buttonFlag);
					e.onReleased(x, y, buttonFlag);
				}
			}
			
			e.handleMouseInput(x, y);
		}
	}
}
