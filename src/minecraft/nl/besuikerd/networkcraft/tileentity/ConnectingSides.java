package nl.besuikerd.networkcraft.tileentity;

import java.util.Arrays;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import net.minecraft.tileentity.TileEntity;
import nl.besuikerd.core.BLogger;
import nl.besuikerd.core.BlockSide;
import nl.besuikerd.core.packet.IProcessData;
import nl.besuikerd.core.utils.BitUtils;

public class ConnectingSides implements IConnectingSides, IProcessData{

	protected byte connectingSides;
	protected TileEntity entity;
	
	public ConnectingSides(TileEntity entity) {
		this.entity = entity;
	}

	@Override
	public void read(ByteArrayDataInput in) {
		connectingSides = in.readByte();
		BLogger.debug("sides: %s", Arrays.toString(BlockSide.fromByte(connectingSides)));
	}

	@Override
	public void write(ByteArrayDataOutput out) {
		out.writeByte(connectingSides);
	}

	@Override
	public boolean connectsTo(IConnectingSides other) {
		return other != null;
	}
	
	@Override
	public void validateConnections() {
		byte oldConnections = connectingSides;
		for(BlockSide side : BlockSide.values()){
			int[] rel = side.getRelativeCoordinates(entity.xCoord, entity.yCoord, entity.zCoord);
			TileEntity other = (TileEntity) entity.worldObj.getBlockTileEntity(rel[0], rel[1], rel[2]);
			
			//set bit for current side if other IConnectingSide exists and connects to this IConnectingSide
			connectingSides = BitUtils.toggle(connectingSides, side.ordinal() + 1, other != null && other instanceof IConnectingSides && entity instanceof IConnectingSides ? ((IConnectingSides) entity).connectsTo((IConnectingSides) other) : connectsTo((IConnectingSides) other));
		}
		BLogger.debug("sides have changed: %b", oldConnections != connectingSides);
		//sides have changed
		if(oldConnections != connectingSides){
			entity.worldObj.markBlockForUpdate(entity.xCoord, entity.yCoord, entity.zCoord);
		}
	}

	@Override
	public BlockSide[] getConnectingSides() {
		return BlockSide.fromByte(connectingSides);
	}

}
