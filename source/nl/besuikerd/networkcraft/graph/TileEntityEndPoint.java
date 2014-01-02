package nl.besuikerd.networkcraft.graph;


public class TileEntityEndPoint extends TileEntityNetworkNode implements IEndPoint{
	
	protected EndPoint endpoint;
	
	public TileEntityEndPoint() {
		this.endpoint = new EndPoint(this, 1);
		node = endpoint;
	}
	
}
