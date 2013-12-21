package nl.besuikerd.networkcraft.tileentity;

import net.minecraft.world.World;
import nl.besuikerd.core.BlockSide;

public interface IConnectingSides {
	public boolean connectsTo(IConnectingSides other);
	public BlockSide[] getConnectingSides();
	public void validateConnections();
}
