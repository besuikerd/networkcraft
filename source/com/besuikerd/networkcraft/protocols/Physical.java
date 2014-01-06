package com.besuikerd.networkcraft.protocols;

import java.util.Arrays;

import com.besuikerd.core.BLogger;

import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.FMLCommonHandler;

public class Physical extends osiLayerDataModel{
	
	public static final String TAG_CONNECTED_SIDES = "CONNCECTEDSIDES";

	private boolean[] connectedSides;
	
	public Physical(){
		connectedSides = new boolean[6];
	}
	
	public boolean[] getConnectedSides(){
		return connectedSides;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {		
		byte val = 0;
		for(int i = 0 ; i < connectedSides.length ; i++){
			val |= ((connectedSides[i] ? 1 : 0) << i) &0xff;
		}
		tagCompound.setByte(TAG_CONNECTED_SIDES, val);
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		byte val = tagCompound.getByte(TAG_CONNECTED_SIDES);
		for(int i = 0 ; i < connectedSides.length ; i++){
			connectedSides[i] = ((val >> i) & 1) == 1;
		}
		BLogger.debug("%s| nbt read, sides: %d %s",FMLCommonHandler.instance().getEffectiveSide(), val, Arrays.toString(connectedSides));
	}
}
