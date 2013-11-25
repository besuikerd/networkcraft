package nl.besuikerd.inetcraft.core;

public enum BlockSide {
	BOTTOM,
	TOP,
	NORTH,
	SOUTH,
	WEST,
	EAST;
	
	public static BlockSide lookup(int side){
		return side >= 0 && side < values().length ? values()[side] : null;
	}
}
