package com.besuikerd.core.gui.element;

import com.besuikerd.core.gui.styler.ElementStylerTexture;
import com.besuikerd.core.gui.texture.scalable.ScalableTexture;
import com.besuikerd.core.utils.Tuple;

import net.minecraft.inventory.Slot;

public class ElementItemContainer extends Element{

	protected Slot slot;
	
	
	public ElementItemContainer(Slot slot, int x, int y) {
		super(x, y, 18, 18);
		this.slot = slot;
	}
	
	public ElementItemContainer(Slot slot){
		this(slot, 0, 0);
	}
	
	@Override
	public void draw(int mouseX, int mouseY) {
		
		//re-align the slot
		slot.xDisplayPosition = absX() - getRoot().absX() + 1;
		slot.yDisplayPosition = absY() - getRoot().absY() + 1;
		
		super.draw(mouseX, mouseY);

		drawBackgroundFromTextures(ScalableTexture.SLOT);
	}
}
