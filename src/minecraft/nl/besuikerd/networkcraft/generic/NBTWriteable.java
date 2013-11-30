package nl.besuikerd.networkcraft.generic;

import net.minecraft.nbt.NBTTagCompound;

public interface NBTWriteable {

	public void writeToNBT(NBTTagCompound tagCompound);
	
	public void readFromNBT(NBTTagCompound tagCompound);

}
