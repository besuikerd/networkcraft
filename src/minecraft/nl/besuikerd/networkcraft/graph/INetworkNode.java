package nl.besuikerd.networkcraft.graph;

import java.util.List;

public interface INetworkNode {
	/**
	 * The master node this node is connected to
	 * @return The master node this node is connected to, or <code>null</code> if no master is connected
	 */
	public INetworkNode getMaster();
	
	/**
	 * The cost to pass this node
	 * @return The cost to pass this node
	 */
	public int getNodeCost();
	
	/**
	 * Nodes that are attached to this node
	 * @return
	 */
	public List<INetworkNode> getConnectedNodes();
	
	/**
	 * x coordinate
	 * @return
	 */
	public int x();
	
	/**
	 * y coordinate
	 * @return
	 */
	public int y();
	
	/**
	 * z coordinate
	 * @return
	 */
	public int z();
}
