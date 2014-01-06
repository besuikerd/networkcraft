package com.besuikerd.networkcraft.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.minecraft.tileentity.TileEntity;

import com.besuikerd.core.BlockSide;
import com.besuikerd.core.packet.IProcessData;
import com.besuikerd.core.utils.collection.SafeConstrainedMap;
import com.besuikerd.core.utils.collection.SmallerThanMapConstraint;
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
		registeredEndPoints.add(endPoint);
	}

	@Override
	public void unregister(IEndPoint endPoint) {
		registeredEndPoints.remove(endPoint);
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
		invalidateConnectedMasters();
		invalidateEndPoints();
		
		invalidateMasters();
		
	}

	public void onDestroyed() {
		this.destroyed = true;
	}

	@Override
	public void invalidateConnectedMasters() {

		Map<INetworkNode, Integer> finishedNodes = new HashMap<INetworkNode, Integer>();
		Map<INetworkNode, Integer> foundNodes = new HashMap<INetworkNode, Integer>();
		foundNodes = SafeConstrainedMap.create(foundNodes, new SmallerThanMapConstraint<INetworkNode, Integer>(foundNodes));
		Map<IMasterNode, Integer> foundMasters = new HashMap<IMasterNode, Integer>();
		foundMasters = SafeConstrainedMap.create(foundMasters, new SmallerThanMapConstraint<IMasterNode, Integer>(foundMasters));

		finishedNodes.put(this, 0); //initialize finished nodes with this node

		findConnectedMasters(this, finishedNodes, foundNodes, foundMasters);

		connectedMasters.clear();
		for (Map.Entry<IMasterNode, Integer> entry : foundMasters.entrySet()) {
			connectedMasters.put(entry.getKey(), entry.getValue());
		}
	}

	private void findConnectedMasters(INetworkNode current, Map<INetworkNode, Integer> finishedNodes, Map<INetworkNode, Integer> foundNodes, Map<IMasterNode, Integer> foundMasters) {
		int currentCost = finishedNodes.get(current);
		for (INetworkNode node : BlockSide.blockSideIterator(INetworkNode.class, entity.worldObj, current.x(), current.y(), current.z())) {
			if (!finishedNodes.containsKey(node)) {
				if (node instanceof IMasterNode) {
					foundMasters.put((IMasterNode) node, currentCost);
				} else {
					foundNodes.put(node, currentCost + node.getNodeCost());
				}
			}
		}

		//determine cheapest nodes
		int currentVal = Integer.MAX_VALUE;
		Set<INetworkNode> cheapestNodes = new HashSet<INetworkNode>();
		for (Map.Entry<INetworkNode, Integer> entry : foundNodes.entrySet()) {
			if (entry.getValue() < currentVal) {
				currentVal = entry.getValue();
				cheapestNodes.clear();
			}
			if (entry.getValue() == currentVal) {
				cheapestNodes.add(entry.getKey());
			}
		}
		for (INetworkNode cheapestNode : cheapestNodes) {
			foundNodes.remove(cheapestNode);
			finishedNodes.put(cheapestNode, currentVal);
			findConnectedMasters(cheapestNode, finishedNodes, foundNodes, foundMasters);
		}
	}
	
	@Override
	public void invalidateEndPoints() {
		endPoints.clear();
		for(IEndPoint endPoint : registeredEndPoints){
			if(endPoint != null) { //to prevent adding an endpoint that has just been deleted
				endPoints.put(endPoint, endPoint.getCost());
			}
		}
		Map<IEndPoint, Integer> newEndPoints = SafeConstrainedMap.create(endPoints, new SmallerThanMapConstraint<IEndPoint, Integer>(endPoints));
		Set<IMasterNode> processedMasters = new HashSet<IMasterNode>();
		findEndPoints(this, 0, processedMasters, newEndPoints);
		
		entity.worldObj.markBlockForUpdate(x(), y(), z());
	}
	
	private void findEndPoints(IMasterNode masterNode, int currentCost, Set<IMasterNode> processedMasters, Map<IEndPoint, Integer> endPoints){
		for(Map.Entry<IMasterNode, Integer> entry : masterNode.getConnectedMasters().entrySet()){	
			for(IEndPoint endpoint : entry.getKey().registeredEndPoints()){
				if(endpoint.getMaster() != null){ //to prevent adding an endpoint that has just been deleted
					endPoints.put(endpoint, currentCost + entry.getValue() + endpoint.getCost());
				}
			}
		}
		processedMasters.add(masterNode);
		for(Map.Entry<IMasterNode, Integer> entry : masterNode.getConnectedMasters().entrySet()){
			if(!processedMasters.contains(entry.getKey())){
				findEndPoints(entry.getKey(), currentCost + entry.getValue() + masterNode.getNodeCost(), processedMasters, endPoints);
			}
		}
	} 

	@Override
	public void read(ByteArrayDataInput in) {
		registeredEndPoints.clear();
		endPoints.clear();
		connectedMasters.clear();

		int size = in.readInt();
		for (int i = 0; i < size; i++) {
			registeredEndPoints.add(new EndPointInitializer(entity, in.readInt(), in.readInt(), in.readInt()));
		}

		size = in.readInt();
		for (int i = 0; i < size; i++) {
			endPoints.put(new EndPointInitializer(entity, in.readInt(), in.readInt(), in.readInt()), in.readInt());
		}

		size = in.readInt();
		for (int i = 0; i < size; i++) {
			connectedMasters.put(new MasterNodeInitializer(entity, in.readInt(), in.readInt(), in.readInt()), in.readInt());
		}
	}

	@Override
	public void write(ByteArrayDataOutput out) {
		out.writeInt(registeredEndPoints.size());
		for (IEndPoint endPoint : registeredEndPoints) {
			out.writeInt(endPoint.x());
			out.writeInt(endPoint.y());
			out.writeInt(endPoint.z());
		}

		out.writeInt(endPoints.size());
		for (Map.Entry<IEndPoint, Integer> entry : endPoints.entrySet()) {
			out.writeInt(entry.getKey().x());
			out.writeInt(entry.getKey().y());
			out.writeInt(entry.getKey().z());
			out.writeInt(entry.getValue());
		}

		out.writeInt(connectedMasters.size());
		for (Map.Entry<IMasterNode, Integer> entry : connectedMasters.entrySet()) {
			out.writeInt(entry.getKey().x());
			out.writeInt(entry.getKey().y());
			out.writeInt(entry.getKey().z());
			out.writeInt(entry.getValue());
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof IMasterNode) {
			IMasterNode ep = (IMasterNode) obj;
			return ep.x() == x() && ep.y() == y() && ep.z() == z();
		}
		return false;
	}
}
