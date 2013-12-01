package nl.besuikerd.networkcraft.gui.element;

import nl.besuikerd.networkcraft.core.NCLogger;
import nl.besuikerd.networkcraft.gui.layout.HoritzontalLayout;

public class ElementItemContainerArray extends ElementContainer{
	
	public ElementItemContainerArray(int x, int y, int count, int columns) {
		super(x, y, columns * 18, (int) (Math.ceil(count / columns) * 18));
		this.layout = new HoritzontalLayout();
		
		NCLogger.debug("itemcontainerarray(%d,%d)", width, height);
		
		for(int i = 0 ; i < count ; i++){
			add(new ElementItemContainer());
		}
	}
	
	public ElementItemContainerArray(int count, int columns) {
		this(0, 0, count, columns);
	}

}
