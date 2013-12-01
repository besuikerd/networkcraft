package nl.besuikerd.networkcraft.generic;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import nl.besuikerd.networkcraft.core.NCLogger;
import nl.besuikerd.networkcraft.protocols.DataLink;
import nl.besuikerd.networkcraft.protocols.Physical;
import nl.besuikerd.networkcraft.protocols.osiLayerDataModel;

public class TileEntityConnecting extends TileEntity{
	
	private osiLayerDataModel osiDataModel;
	
	//zullen we dit naar physical layer klasse gooien voor netheid, of is dat te moeilijk gedacht?
	//private boolean[] connectedSides;
	
	public TileEntityConnecting() {
		this.osiDataModel = new DataLink();
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
		osiDataModel.writeToNBT(tag);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		osiDataModel.readFromNBT(tag);
	}
	
	public boolean[] getConnectedSides() {
		return ((Physical) osiDataModel).getConnectedSides();
	}
}
