package nl.besuikerd.networkcraft.core;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import nl.besuikerd.networkcraft.core.packet.PacketTileUpdate;
import cpw.mods.fml.relauncher.Side;

public abstract class TileEntityNC extends TileEntity{
	
	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound tagCompound = new NBTTagCompound();
	    writeToNBT(tagCompound);
	    return new Packet132TileEntityData(xCoord, yCoord, zCoord, 1, tagCompound);
	}
	
	@Override
	public void onDataPacket(INetworkManager net, Packet132TileEntityData packet) {
		readFromNBT(packet.data);
	}
	
	/**
	 * method gets called after a {@link PacketTileUpdate} has been processed by this tile entity
	 * @param player
	 * @param side
	 */
	public void onPostReceive(EntityPlayer player, Side side){}
}
