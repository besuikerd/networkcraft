package nl.besuikerd.core.tileentity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

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
	
	public void onTileEntityPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack){}
	public void onRemoveTileEntity(World world, int x, int y, int z){}
	public void onTileEntityRemoved(World world, int x, int y, int z) {}
}
