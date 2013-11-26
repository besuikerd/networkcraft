package nl.besuikerd.networkcraft.generic;

import java.util.Arrays;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import nl.besuikerd.networkcraft.core.NCLogger;

public class TileEntityConnecting extends TileEntity{
	
	public static final String TAG_CONNECTED_SIDES = "sides";
	
	private boolean[] connectedSides;
	
	public TileEntityConnecting() {
		this.connectedSides = new boolean[6];
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		byte val = tag.getByte(TAG_CONNECTED_SIDES);
		for(int i = 0 ; i < connectedSides.length ; i++){
			connectedSides[i] = ((val >> i) & 1) == 1;
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		
		byte val = 0;
		for(int i = 0 ; i < connectedSides.length ; i++){
			val |= ((connectedSides[i] ? 1 : 0) << i) &0xff;
		}
		tag.setByte(TAG_CONNECTED_SIDES, val);
	}
	
	public boolean[] getConnectedSides() {
		return connectedSides;
	}
}
