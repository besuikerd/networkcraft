package nl.besuikerd.networkcraft.gui;

import nl.besuikerd.networkcraft.core.utils.INumbered;

public enum NCGuiId implements INumbered{
	TEST(0),
	INVENTORYTEST(1)
	
	;
	
	private int number;

	private NCGuiId(int number) {
		this.number = number;
	}
	
	@Override
	public int getNumber() {
		return 0;
	}
}
