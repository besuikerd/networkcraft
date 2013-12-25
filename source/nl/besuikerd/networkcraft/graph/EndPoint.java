package nl.besuikerd.networkcraft.graph;

import javax.xml.ws.Endpoint;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import nl.besuikerd.core.BlockSide;
import nl.besuikerd.core.ServerLogger;
import nl.besuikerd.core.utils.MathUtils;

public class EndPoint extends NetworkNode implements IEndPoint {

	public EndPoint(TileEntity entity, int nodeCost) {
		super(entity, nodeCost);
	}

	@Override
	public void onPlaced() {
		findCheapestNode();
		if(master != null){
			master.register(this);
		}
		invalidateMasters();
	}

	@Override
	protected void onDestroyed() {
		if(master != null){
			master.unregister(this);
		}
		super.onDestroyed();
	}
	
	@Override
	public void onNodeChanged(BlockSide side) {
		IMasterNode oldMaster = master;
		super.onNodeChanged(side);		
		if(oldMaster != null && (master == null  || master != oldMaster)){
			oldMaster.unregister(this);
		}
		if(master != null && !master.equals(oldMaster)){
			master.register(this);
		}
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
