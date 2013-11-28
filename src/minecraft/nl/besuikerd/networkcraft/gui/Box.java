package nl.besuikerd.networkcraft.gui;

import java.util.ArrayList;
import java.util.List;

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
	
	protected List<Element> elementsAt(int x, int y){
		List<Element> result = new ArrayList<Element>();
		for(Element e : elements){
			if(MathUtils.inRange(x, e.absX(), e.absX() + e.width) && MathUtils.inRange(y, e.absY(), e.absY() + e.height)) {
				result.add(e);
			}
		}
		return result;
	}
	
	@Override
	protected void handleMouseInput(int x, int y) {
		super.handleMouseInput(x, y);
		
	}
	
	@Override
	protected void mouseClicked(int x, int y, int eventButton) {
		super.mouseClicked(x, y, eventButton);
		for(Element e : elementsAt(x, y)){
			e.mouseClicked(x, y, eventButton);
		}
	}
	
	@Override
	protected void mouseMovedOrUp(int x, int y, int eventButton) {
		super.mouseMovedOrUp(x, y, eventButton);
		super.mouseClicked(x, y, eventButton);
		for(Element e : elementsAt(x, y)){
			e.mouseMovedOrUp(x, y, eventButton);
		}
	}
}
