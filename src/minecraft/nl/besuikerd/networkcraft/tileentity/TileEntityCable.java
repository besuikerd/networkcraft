package nl.besuikerd.networkcraft.tileentity;

import cpw.mods.fml.relauncher.Side;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import nl.besuikerd.core.BlockSide;
import nl.besuikerd.core.utils.NBTUtils;
import nl.besuikerd.networkcraft.graph.TileEntityNetworkNode;

public class TileEntityCable extends TileEntityNetworkNode implements IConnectingSides{
	public static final String TAG_CONNECTED_SIDES = "SIDES";
	
	protected ConnectingSides connectingSides;
	
	public TileEntityCable() {
		this.connectingSides = new ConnectingSides(this);
	}
	
	@Override
	public boolean connectsTo(IConnectingSides other) {
		return connectingSides.connectsTo(other);
	}

	@Override
	public BlockSide[] getConnectingSides() {
		return connectingSides.getConnectingSides();
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
}
