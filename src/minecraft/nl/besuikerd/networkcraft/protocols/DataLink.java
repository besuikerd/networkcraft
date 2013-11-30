package nl.besuikerd.networkcraft.protocols;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import nl.besuikerd.networkcraft.generic.NBTWriteable;

// DataLink extends physical layer because cables also keep track of the data link layer for connection reasons.
public class DataLink extends PhysicalLink{

	// I guess using 2 variables is more efficient compared to 2 list/arrays
	private MacAddress macA, macB;
	private TileEntity deviceA, deviceB;
	
	public boolean addAddress(MacAddress mac, TileEntity device){
		if(macA == null){
			macA = mac;
			deviceA = device;
		}else if (macB == null){
			macB = mac;
			deviceB = device;
		}else{
			return false;
		}
		return true;
	}
	
	public boolean canAddAddress(){
		if(macA==null || macB == null){
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		
	}
}
