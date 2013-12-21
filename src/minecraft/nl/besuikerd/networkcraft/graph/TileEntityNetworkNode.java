package nl.besuikerd.networkcraft.graph;

import java.util.ArrayList;
import java.util.List;

import nl.besuikerd.networkcraft.tileentity.TileEntityBesu;

public class TileEntityNetworkNode extends TileEntityBesu implements INetworkNode{

	protected INetworkNode master;
	protected List<INetworkNode> connectedNodes;
	protected int nodeCost;
	
	public TileEntityNetworkNode() {
		this.connectedNodes = new ArrayList<INetworkNode>();
		this.nodeCost = 5;
	}
	
	@Override
	public INetworkNode getMaster() {
		return master;
	}

	@Override
	public int getNodeCost() {
		return nodeCost;
	}

	@Override
	public List<INetworkNode> getConnectedNodes() {
		return connectedNodes;
	}

	@Override
	public int x() {
		return xCoord;
	}

	@Override
	public int y() {
		return yCoord;
	}

	@Override
	public int z() {
		return zCoord;
	}

}
