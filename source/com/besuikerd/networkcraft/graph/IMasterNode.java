package com.besuikerd.networkcraft.graph;

import java.util.Map;
import java.util.Set;

public interface IMasterNode extends INetworkNode {
	/**
	 * Called to register an IEndPoint to this IMasterNode.
	 * 
	 * @param endPoint
	 */
	public void register(IEndPoint endPoint);

	/**
	 * Called whenever an IEndPoint is disconnected from this IMasterNode.
	 * Should also be called when the given IEndPoint finds a shorter path to a
	 * different IMasterNode before registering to that new IMasterNode
	 * 
	 * @param endPoint
	 */
	public void unregister(IEndPoint endPoint);

	/**
	 * set containing all IEndPoints registered to this IMasterNode
	 * 
	 * @return
	 */
	public Set<IEndPoint> registeredEndPoints();

	/**
	 * map containing all IEndPoints in the whole network with their
	 * corresponding costs
	 * 
	 * @return
	 */
	public Map<IEndPoint, Integer> endPoints();

	/**
	 * mapping between all IMasterNodes connected to this IMasterNode and their
	 * relative costs to this node
	 * 
	 * @return
	 */
	public Map<IMasterNode, Integer> getConnectedMasters();

	/**
	 * invalidate this IMasterNode; allows it to recalculate it's nearby masters
	 */
	public void invalidateConnectedMasters();
	
	/**
	 * Invalidate this IMasterNode to allow it to recalculate endpoint costs
	 */
	public void invalidateEndPoints();

}
