package nl.besuikerd.networkcraft.graph;

public interface IMasterNode extends INetworkNode{
	public void register(IEndPoint endPoint);
	public void unregister(IEndPoint endPoint);
}
