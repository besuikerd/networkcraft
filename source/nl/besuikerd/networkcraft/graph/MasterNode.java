package nl.besuikerd.networkcraft.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.minecraft.tileentity.TileEntity;
import nl.besuikerd.core.BlockSide;
import nl.besuikerd.core.ServerLogger;
import nl.besuikerd.core.packet.IProcessData;
import nl.besuikerd.core.utils.collection.SafeConstrainedMap;
import nl.besuikerd.core.utils.collection.SmallerThanMapConstraint;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

public class MasterNode extends NetworkNode implements IMasterNode, IProcessData {

	protected boolean destroyed = false;

	protected Set<IEndPoint> registeredEndPoints;
	protected Map<IEndPoint, Integer> endPoints;
	protected Map<IMasterNode, Integer> connectedMasters;
	
	
	public MasterNode(TileEntity entity, int cost) {
		super(entity, cost);
		this.registeredEndPoints = new HashSet<IEndPoint>();
		this.endPoints = new HashMap<IEndPoint, Integer>();
		this.connectedMasters = new HashMap<IMasterNode, Integer>();
	}

	@Override
	public IMasterNode getMaster() {
		return destroyed ? null : this;
	}

	@Override
	public int getNodeCost() {
		return nodeCost;
	}

	@Override
	public int getCost() {
		return destroyed ? Integer.MAX_VALUE : nodeCost;
	}

	@Override
	public BlockSide getDirection() {
		return null;
	}

	@Override
	public void onNodeChanged(BlockSide side) {
		//nothing to do here! =)
	}

	@Override
	public void register(IEndPoint endPoint) {
		ServerLogger.debug("node registered at master %s, %s[cost=%d, coords=(%d,%d,%d)]", this, endPoint.getClass().getName(), endPoint.getNodeCost(), endPoint.x(), endPoint.y(), endPoint.z());
	}

	@Override
	public void unregister(IEndPoint endPoint) {
		ServerLogger.debug("node unregistered at master %s, %s[cost=%d, coords=(%d,%d,%d)]", this, endPoint.getClass().getName(), endPoint.getNodeCost(), endPoint.x(), endPoint.y(), endPoint.z());
	}

	@Override
	public Set<IEndPoint> registeredEndPoints() {
		return registeredEndPoints;
	}

	@Override
	public Map<IEndPoint, Integer> endPoints() {
		return endPoints;
	}

	@Override
	public Map<IMasterNode, Integer> getConnectedMasters() {
		return connectedMasters;
	}

	public void onPlaced() {
		updateNetwork();
	}

	public void onDestroyed() {
		this.destroyed = true;
	}
	
	/*
	@Override
	public void invalidate(boolean broadcast) {
		Map<IMasterNode, Integer> newMap = new HashMap<IMasterNode, Integer>();
		for(BlockSide side : BlockSide.values()){
			int[] rel = side.getRelativeCoordinates(x(), y(), z());
			TileEntity tile = entity.worldObj.getBlockTileEntity(rel[0], rel[1], rel[2]);
			if(tile != null && tile instanceof INetworkNode){
				INetworkNode node = (INetworkNode) tile;
				if(node.getMaster() != null && !equals(node.getMaster())){
					newMap.put(node.getMaster(), node.getCost());
				}
				if(node.getDirection() != null && node.getDirection() == side.opposite()){
					findMastersFromNode(node, newMap);
				}
			}
		}
		if(broadcast){
			for(Map.Entry<IMasterNode, Integer> entry : newMap.entrySet()){
				Integer oldCost = connectedMasters.get(entry.getKey());
				if(oldCost == null){
					oldCost = Integer.MAX_VALUE;
				}
				if(oldCost > entry.getValue()){
					entry.getKey().invalidate(false);
				}
			}
		}
	}
	*/
	
	public void invalidate(INetworkNode addedNode, Map<IMasterNode,Integer> costs) {
		Map<IMasterNode, Integer> newConnectedMasters = SafeConstrainedMap.create(connectedMasters, new SmallerThanMapConstraint<IMasterNode, Integer>(connectedMasters));
		
		if(addedNode == null){
			for(Map.Entry<IMasterNode, Integer> entry : costs.entrySet()){
				newConnectedMasters.put(entry.getKey(), entry.getValue());
			}
		} else {
			int selfCost = costs.get(this);
			if(addedNode instanceof IMasterNode){ //add if node added was a master node
				newConnectedMasters.put((IMasterNode)addedNode, selfCost);
				
			} else{
				for(Map.Entry<IMasterNode, Integer> entry : costs.entrySet()){ //add other nodes to connected masters
					if(!entry.getKey().equals(this)){
						newConnectedMasters.put(entry.getKey(), entry.getValue() + selfCost + addedNode.getNodeCost());
					}
				}
			}
		}
		ServerLogger.debug("connected masters: %s", newConnectedMasters.values());
		entity.worldObj.markBlockForUpdate(x(), y(), z());
	};
	
	@Override
	public void invalidateRemoval(INetworkNode removedNode, Set<IMasterNode> removedMasters) {
		for(IMasterNode master : removedMasters){
			connectedMasters.remove(master);
		}
	}
	
	private void findMastersFromNode(INetworkNode node, Map<IMasterNode, Integer> map){
		for(BlockSide side : BlockSide.values()){
			if(side != node.getDirection()){
				TileEntity tile = entity.worldObj.getBlockTileEntity(node.x(), node.y(), node.z());
				if(tile != null && tile instanceof INetworkNode){
					INetworkNode other = (INetworkNode) tile;
					if(other.getDirection() == node.getDirection().opposite()){
						findMastersFromNode(other, map);
					} else if(other.getDirection() != null && other.getMaster() != null && !node.getMaster().equals(other.getMaster())){
						Integer cost = map.get(other.getMaster());
						if(cost == null){
							cost = Integer.MAX_VALUE;
						}
						int realCost = -1;
						if(cost > (realCost = (node.getCost() - node.getMaster().getNodeCost()) + (other.getCost() - other.getMaster().getCost())) ){
							map.put(other.getMaster(), node.getCost() + other.getCost());
						}
					}
				}
			}
		}
	}
	
	@Override
	public void read(ByteArrayDataInput in) {
		registeredEndPoints.clear();
		endPoints.clear();
		connectedMasters.clear();
		
		int size = in.readInt();
		for(int i = 0 ; i < size ; i++){
			registeredEndPoints.add(new EndPointInitializer(entity, in.readInt(), in.readInt(), in.readInt()));
		}
		
		size = in.readInt();
		for(int i = 0 ; i < size ; i++){
			endPoints.put(new EndPointInitializer(entity, in.readInt(), in.readInt(), in.readInt()), in.readInt());
		}
		
		size = in.readInt();
		for(int i = 0 ; i < size ; i++){
			connectedMasters.put(new MasterNodeInitializer(entity, in.readInt(), in.readInt(), in.readInt()), in.readInt());
		}
	}
	
	@Override
	public void write(ByteArrayDataOutput out) {
		out.writeInt(registeredEndPoints.size());
		for(IEndPoint endPoint : registeredEndPoints){
			out.writeInt(endPoint.x());
			out.writeInt(endPoint.y());
			out.writeInt(endPoint.z());
		}
		
		out.writeInt(endPoints.size());
		for(Map.Entry<IEndPoint, Integer> entry : endPoints.entrySet()){
			out.writeInt(entry.getKey().x());
			out.writeInt(entry.getKey().y());
			out.writeInt(entry.getKey().z());
			out.writeInt(entry.getValue());
		}
		
		out.writeInt(connectedMasters.size());
		for(Map.Entry<IMasterNode, Integer> entry : connectedMasters.entrySet()){
			out.writeInt(entry.getKey().x());
			out.writeInt(entry.getKey().y());
			out.writeInt(entry.getKey().z());
			out.writeInt(entry.getValue());
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
