package nl.besuikerd.networkcraft.tileentity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import nl.besuikerd.core.BlockSide;
import nl.besuikerd.core.utils.NBTUtils;
import nl.besuikerd.networkcraft.graph.IMasterNode;
import nl.besuikerd.networkcraft.graph.INetworkNode;
import nl.besuikerd.networkcraft.graph.NetworkNode;
import nl.besuikerd.networkcraft.graph.TileEntityNetworkNode;

public class TileEntityCable extends TileEntityNetworkNode implements IConnectingSides{
	public static final String TAG_CONNECTED_SIDES = "SIDES";
	
	protected ConnectingSides connectingSides;
	
	public TileEntityCable() {
		this.connectingSides = new ConnectingSides(this);
		this.node = new NetworkNode(this, 1);
	}
	
	@Override
	public boolean connectsTo(TileEntity other) {
		return other instanceof INetworkNode && !(getMaster() != null && other instanceof IMasterNode);
	}

	@Override
	public BlockSide[] getConnectingSides() {
//		return connectingSides.getConnectingSides();
		return node.getDirection() == null ? new BlockSide[0] : new BlockSide[]{node.getDirection()};
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		NBTUtils.writeProcessData(connectingSides, tag, TAG_CONNECTED_SIDES);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		NBTUtils.readProcessData(connectingSides, tag, TAG_CONNECTED_SIDES);
	}

	@Override
	public void validateConnections() {
		connectingSides.validateConnections();
	}
	
	@Override
	public void validateNeighbours() {
		connectingSides.validateNeighbours();
	}

	@Override
	public void onTileEntityPlacedBy(World world, int x, int y, int z,
			EntityLivingBase entity, ItemStack stack) {
		super.onTileEntityPlacedBy(world, x, y, z, entity, stack);
		validateConnections();
		validateNeighbours();
	}
	
	@Override
	public void onTileEntityRemoved(World world, int x, int y, int z) {
		super.onTileEntityRemoved(world, x, y, z);
		validateNeighbours();
	}
}
