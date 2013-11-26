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
		byte val = tag.getByte(TAG_CONNECTED_SIDES);
		NCLogger.debug("reading nbt...");
		this.connectedSides = new boolean[6];
		for(int i = 0 ; i < connectedSides.length ; i++){
			connectedSides[i] = ((val >> i) & 1) == 1;
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound par1nbtTagCompound) {
		super.writeToNBT(par1nbtTagCompound);
		NCLogger.debug("writing nbt...");
		byte val = 0;
		for(int i = 0 ; i < connectedSides.length ; i++){
			val |= ((connectedSides[i] ? 1 : 0) << i) &0xff;
		}
	}
	
	public boolean[] getConnectedSides() {
		return connectedSides;
	}
}
