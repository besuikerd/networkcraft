package nl.besuikerd.networkcraft.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

public class TileEntityBesu extends TileEntity{
	@Override
    public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt) {
            super.onDataPacket(net, pkt);
            readFromNBT(pkt.data);
    }
	
	@Override
    public Packet getDescriptionPacket() {
            NBTTagCompound tag = new NBTTagCompound();
            writeToNBT(tag);
            return new Packet132TileEntityData(xCoord, yCoord, zCoord, 1, tag);
    }
}
