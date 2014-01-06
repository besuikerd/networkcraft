package com.besuikerd.networkcraft.protocols;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

// DataLink extends physical layer because cables also keep track of the data link layer for connection reasons.
public class DataLink extends Physical{
	
	public static final String WORLDAID = "WORLDAID", WORLDBID = "WORLDBID", MACA = "MACA", MACB = "MACB", XA = "XA", XB = "XB", YA = "YA", YB = "YB", ZA = "ZA", ZB = "ZB";

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
		super.writeToNBT(tagCompound);

		if(deviceA != null){
			tagCompound.setInteger(WORLDAID, deviceA.getWorldObj().provider.dimensionId);
			tagCompound.setInteger(XA, deviceA.xCoord);
			tagCompound.setInteger(YA, deviceA.yCoord);
			tagCompound.setInteger(ZA, deviceA.zCoord);
		}
		if(deviceB != null){
			tagCompound.setInteger(WORLDBID, deviceB.getWorldObj().provider.dimensionId);
			tagCompound.setInteger(XB, deviceB.xCoord);
			tagCompound.setInteger(YB, deviceB.yCoord);
			tagCompound.setInteger(ZB, deviceB.zCoord);
		}
		if(macA != null)
			tagCompound.setInteger(MACA, macA.getMac());
		if(macB != null)
			tagCompound.setInteger(MACB, macB.getMac());
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		
		int worldA = tagCompound.getInteger(WORLDAID);
		int worldB = tagCompound.getInteger(WORLDBID);
		World world = DimensionManager.getWorld(worldA);
		if(world != null)
			deviceA = world.getBlockTileEntity(tagCompound.getInteger(XA), tagCompound.getInteger(YA), tagCompound.getInteger(ZA));
		if(worldA != worldB)
			world = DimensionManager.getWorld(worldB);
		if(world != null)
			deviceB = world.getBlockTileEntity(tagCompound.getInteger(XB), tagCompound.getInteger(YB), tagCompound.getInteger(ZB));
		
		if(tagCompound.getInteger(MACA) != 0)
			this.macA = new MacAddress(tagCompound.getInteger(MACA));
		if(tagCompound.getInteger(MACB) != 0)
			this.macB = new MacAddress(tagCompound.getInteger(MACB));
	}
}
