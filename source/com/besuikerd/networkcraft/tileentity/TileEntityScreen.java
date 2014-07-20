package com.besuikerd.networkcraft.tileentity;

import net.minecraft.tileentity.TileEntity;

import com.besuikerd.tileentity.CompositeTileEntity;
import com.besuikerd.tileentity.TileEntityLogic;
import com.besuikerd.tileentity.TileEntityLogicConnecting;
import com.besuikerd.utils.BlockSide;

public class TileEntityScreen extends CompositeTileEntity{
	
	private TileEntityLogicConnecting connecting;
	
	@Override
	protected TileEntityLogic[] createCompostion() {
		TileEntityLogic[] logics = {
			new TileEntityLogicConnecting(this, new ConnectsToPredicate())
		};
		return logics;
	}
	
	
	private class ConnectsToPredicate implements TileEntityLogicConnecting.ConnectsToPredicate{
		@Override
		public boolean connectsTo(TileEntity other, BlockSide side) {
			return other.getBlockMetadata() == getBlockMetadata() && BlockSide.values()[getBlockMetadata()].isOnSameAxis(side);
		}
	}
}
