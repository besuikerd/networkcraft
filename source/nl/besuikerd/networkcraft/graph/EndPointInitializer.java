package nl.besuikerd.networkcraft.graph;

import net.minecraft.tileentity.TileEntity;

public class EndPointInitializer extends NetworkNodeInitializerBase<IEndPoint> implements IEndPoint{

	public EndPointInitializer(TileEntity entity, int x, int y, int z) {
		super(entity, x, y, z);
	}

	@Override
	public Class<IEndPoint> getClassInstance() {
		return IEndPoint.class;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof IEndPoint){
			IEndPoint ep = (IEndPoint) obj;
			return ep.x() == x() && ep.y() == y() && ep.z() == z();
		}
		return false;
	}
}
