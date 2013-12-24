package nl.besuikerd.networkcraft.graph;

import java.util.List;

import nl.besuikerd.core.BlockSide;

public interface INetworkNode {
	/**
	 * the master node this node is connected to
	 * 
	 * @return the master node this node is connected to, or <code>null</code>
	 *         if no master is connected
	 */
	public IMasterNode getMaster();

	/**
	 * cost to pass this node
	 * 
	 * @return the cost to pass this node
	 */
	public int getNodeCost();

	/**
	 * total cost to get from this node to the root
	 * 
	 * @return the total cost to get from this node to the root
	 */
	public int getCost();

	/**
	 * direction this node passes information to
	 * 
	 * @return the direction this node passes information to
	 */
	public BlockSide getDirection();

	/**
	 * called when a nearby node is changed. Call it twice if a node has been
	 * removed to allow the other nodes to rediscover shorter paths.
	 * 
	 * @param side
	 *            the side of the node that has been changed
	 */
	public void onNodeChanged(BlockSide side);

	/**
	 * x coordinate
	 * 
	 * @return
	 */
	public int x();

	/**
	 * y coordinate
	 * 
	 * @return
	 */
	public int y();

	/**
	 * z coordinate
	 * 
	 * @return
	 */
	public int z();

	/**
	 * called when the addition or removal of this INetworkNode causes an update
	 * to the network
	 */
	public void updateNetwork();
}
