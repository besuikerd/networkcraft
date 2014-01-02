package nl.besuikerd.core.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nl.besuikerd.core.utils.INumbered;

public enum GuiId implements INumbered{
	TEST(0),
	INVENTORYTEST(1)
	
	;
	
	private int number;

	private GuiId(int number) {
		this.number = number;
	}
	
	@Override
	public int getNumber() {
		return 0;
	}
}
