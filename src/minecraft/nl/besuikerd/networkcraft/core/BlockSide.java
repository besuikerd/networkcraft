package nl.besuikerd.networkcraft.core;

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
	
	public int[] getRelativeCoordinates(int x, int y, int z, int distance){
		switch(this){
		case TOP: 		return new int[]{x, y + distance, z};
		case BOTTOM: 	return new int[]{x, y - distance, z};
		case NORTH:		return new int[]{x, y, z - distance};
		case SOUTH:		return new int[]{x, y, z + distance};
		case WEST:		return new int[]{x - distance, y, z};
		case EAST:		return new int[]{x + distance, y, z};
		default:		return new int[]{x,y,z};
		}
	}
	
	public int[] getRelativeCoordinates(int x, int y, int z){
		return getRelativeCoordinates(x, y, z, 1);
	}
}
