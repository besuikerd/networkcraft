package nl.besuikerd.networkcraft.generic;

import java.util.Arrays;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
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
		NCLogger.debug("%s| nbt read, sides: %d %s",FMLCommonHandler.instance().getEffectiveSide(), val, Arrays.toString(connectedSides));
	}
	
	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound tag = new NBTTagCompound();
		writeToNBT(tag);
		return new Packet132TileEntityData(xCoord, yCoord, zCoord, 1, tag);
	}
	
	@Override
	public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt) {
		super.onDataPacket(net, pkt);
		NCLogger.debug("%s", net.getClass());
		readFromNBT(pkt.data);
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
