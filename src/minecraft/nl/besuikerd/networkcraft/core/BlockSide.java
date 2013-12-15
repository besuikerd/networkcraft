package nl.besuikerd.networkcraft.core;

import java.util.Arrays;

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
	
	/**
	 * returns Blockside opposite of this BlockSide
	 * @return
	 */
	public BlockSide opposite(){
		return values()[ordinal() + (ordinal() % 2 == 0 ? 1 : -1)];
	}
	
	/**
	 * turns an array of blocksides to a byte representation
	 * @param blockSides
	 * @return
	 */
	public static byte toByte(BlockSide... blockSides){
		byte result = 0;
		for(BlockSide b : blockSides){
			result |= 1 << b.ordinal();
		}
		return result;
	}
	
	public static BlockSide[] fromByte(byte sides){
		BlockSide[] result = new BlockSide[Integer.bitCount(sides & 0xff)];
		int current = 0;
		for(int i = 0 ; i < 6 ; i++){
			 if(((sides >> i) & 1) == 1){
				 result[current] = BlockSide.values()[i];
				 current++;
			 }
		}
		return result;
	}
	
	public static boolean isSideSelected(byte selectedSides, int side){
		return ((selectedSides >> side) & 1) == 1;
	}
	
	public static boolean isSideSelected(byte selectedSides, BlockSide side){
		return isSideSelected(selectedSides, side.ordinal());
	}
}
