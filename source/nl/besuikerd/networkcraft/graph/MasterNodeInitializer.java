package nl.besuikerd.networkcraft.graph;

import java.util.Map;
import java.util.Set;

import net.minecraft.tileentity.TileEntity;
import nl.besuikerd.core.BlockSide;

/**
 * wrapped around a TileEntity to simulate a reference to a masternode. Once one
 * of it's methods is called, it will try to find the given TileEntity
 * 
 * @author Besuikerd
 * 
 */
public class MasterNodeInitializer extends NetworkNodeInitializerBase<IMasterNode> implements IMasterNode {

	public MasterNodeInitializer(TileEntity entity, int x, int y, int z) {
		super(entity, x, y, z);
	}
	
	@Override
	public Class<IMasterNode> getClassInstance() {
		return IMasterNode.class;
	}

	
	@Override
	public void register(IEndPoint endPoint) {
		checkExists();
		if (instance != null) {
			instance.register(endPoint);
		}
	}

	@Override
	public void unregister(IEndPoint endPoint) {
		checkExists();
		if (instance != null) {
			instance.unregister(endPoint);
		}
	}

	@Override
	public Set<IEndPoint> registeredEndPoints() {
		checkExists();
		if(instance != null){
			return instance.registeredEndPoints();
		}
		return null;
	}

	@Override
	public Map<IEndPoint, Integer> endPoints() {
		checkExists();
		if(instance != null){
			return instance.endPoints();
		}
		return null;
	}

	@Override
	public Map<IMasterNode, Integer> getConnectedMasters() {
		checkExists();
		if(instance != null){
			return instance.getConnectedMasters();
		}
		return null;
	}
	
	@Override
	public void invalidate(INetworkNode addedNode, Map<IMasterNode, Integer> costs) {
		checkExists();
		if(instance != null){
			instance.invalidate(addedNode, costs);
		}
	}

	@Override
	public void invalidateRemoval(INetworkNode removedNode, Set<IMasterNode> removedMasters) {
		checkExists();
		if(instance != null){
			instance.invalidateRemoval(removedNode, removedMasters);
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof IMasterNode){
			IMasterNode ep = (IMasterNode) obj;
			return ep.x() == x() && ep.y() == y() && ep.z() == z();
		}
		return false;
	}
}
