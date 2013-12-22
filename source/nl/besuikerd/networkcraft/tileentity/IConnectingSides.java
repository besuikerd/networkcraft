package nl.besuikerd.networkcraft.tileentity;

import net.minecraft.tileentity.TileEntity;
import nl.besuikerd.core.BlockSide;

public interface IConnectingSides {
	public boolean connectsTo(TileEntity other);
	public BlockSide[] getConnectingSides();
	public void validateConnections();
	public void validateNeighbours();
}
