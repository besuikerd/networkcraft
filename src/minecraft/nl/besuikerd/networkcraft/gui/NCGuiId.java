package nl.besuikerd.networkcraft.gui;

public enum NCGuiId implements INumbered{
	TEST(0);
	
	private int number;

	private NCGuiId(int number) {
		this.number = number;
	}
	
	@Override
	public int getNumber() {
		return 0;
	}
}
