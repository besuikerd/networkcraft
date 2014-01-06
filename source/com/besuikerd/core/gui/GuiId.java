package com.besuikerd.core.gui;

import com.besuikerd.core.utils.INumbered;

public enum GuiId implements INumbered{
	GUITEST(0),
	INVENTORYTEST(1)
	
	;
	
	private int number;

	private GuiId(int number) {
		this.number = number;
	}
	
	@Override
	public int getNumber() {
		return number;
	}
}
