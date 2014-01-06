package com.besuikerd.networkcraft.graph;

import net.minecraft.tileentity.TileEntity;

public class NetworkNodeInitializer extends NetworkNodeInitializerBase<INetworkNode>{
	
	public NetworkNodeInitializer(TileEntity entity, int x, int y, int z) {
		super(entity, x, y, z);
	}
	
	@Override
	public Class<INetworkNode> getClassInstance() {
		return INetworkNode.class;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof INetworkNode){
			INetworkNode ep = (INetworkNode) obj;
			return ep.x() == x() && ep.y() == y() && ep.z() == z();
		}
		return false;
	}
}
