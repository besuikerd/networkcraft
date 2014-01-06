package com.besuikerd.networkcraft.tileentity;

import com.besuikerd.core.BlockSide;

import net.minecraft.tileentity.TileEntity;

public interface IConnectingSides {
	public boolean connectsTo(TileEntity other);
	public BlockSide[] getConnectingSides();
	public void validateConnections();
	public void validateNeighbours();
}
