package nl.besuikerd.networkcraft.graph;

import net.minecraft.tileentity.TileEntity;
import nl.besuikerd.core.BlockSide;
import nl.besuikerd.core.ServerLogger;
import nl.besuikerd.core.packet.IProcessData;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

public class NetworkNode implements INetworkNode, IProcessData{

	protected IMasterNode master;
	protected int cost = -1;
	protected int nodeCost;
	protected BlockSide direction;
	protected TileEntity entity;
	
	public NetworkNode(TileEntity entity, int nodeCost) {
		this.entity = entity;
		this.nodeCost = nodeCost;
	}
	
	@Override
	public void read(ByteArrayDataInput in) {
		if(in.readBoolean()){
			TileEntity tile = entity.worldObj.getBlockTileEntity(in.readInt(), in.readInt(), in.readInt());
			if(tile != null && tile instanceof IMasterNode){
				this.master = (IMasterNode) tile;
			}
		}
		this.cost = in.readInt();
		int dir = in.readInt();
		this.direction = dir == -1 ? null : BlockSide.values()[dir];
	}

	@Override
	public void write(ByteArrayDataOutput out) {
		out.writeBoolean(master != null);
		if(master != null){
			out.writeInt(master.x());
			out.writeInt(master.y());
			out.writeInt(master.z());
		}
		out.writeInt(cost);
		out.writeInt(direction == null ? -1 : direction.ordinal());
	}

	@Override
	public IMasterNode getMaster() {
		return master;
	}

	@Override
	public int getNodeCost() {
		return nodeCost;
	}
	
	@Override
	public int getCost() {
		return cost < 0 ? Integer.MAX_VALUE : cost + nodeCost;
	}

	@Override
	public BlockSide getDirection() {
		return direction;
	}

	@Override
	public int x() {
		return entity.xCoord;
	}

	@Override
	public int y() {
		return entity.yCoord;
	}

	@Override
	public int z() {
		return entity.zCoord;
	}

	protected void onPlaced(){
		findCheapestNode();
	}
	
	protected void onDestroyed(){
		this.master = null;
		this.direction = null;
		this.cost = -1;
		postNodeChanged(); //clear all nodes unreachable because of destroy
		postNodeChanged(); //enable nodes to look for alternative paths
	}

	
	@Override
	public void onNodeChanged(BlockSide side) {
		int[] rel = side.getRelativeCoordinates(x(), y(), z());
		TileEntity tile = entity.worldObj.getBlockTileEntity(rel[0], rel[1], rel[2]);
		if(tile != null && tile instanceof INetworkNode){
			INetworkNode node = (INetworkNode) tile;
			if(side == direction){
				if(master == null){ //node was declared unreachable one update before
					this.direction = null;
					findCheapestNode();
					postNodeChanged();
				} else if(node.getMaster() == null){ //check if direction is unreachable
					this.master = null;
					this.cost = -1;
					postNodeChanged();
				} else if(node.getCost() > this.cost){ //check if direction is more expensive now
					findCheapestNode();
				} else if(node.getCost() < this.cost){ //check if direction is cheaper now
					this.cost = node.getCost();
					postNodeChanged();
				}
			} else if(node.getMaster() != null && (node.getCost() < this.cost || this.master == null)){ //check if this direction is cheaper now
				this.direction = side;
				this.master = node.getMaster();
				this.cost = node.getCost();
				postNodeChanged();
			}
		}
	}
	
	/**
	 * look around the node to find the cheapest node. If it is different than
	 * the current node, notify all nodes around this node that this node has
	 * been changed.
	 */
	private void findCheapestNode(){
		boolean cheapestNodeUpdated = false;
		for(BlockSide side : BlockSide.values()){
			int[] rel = side.getRelativeCoordinates(x(), y(), z());
			TileEntity tile = entity.worldObj.getBlockTileEntity(rel[0], rel[1], rel[2]);
			if(tile != null && tile instanceof INetworkNode){
				INetworkNode node = (INetworkNode) tile;
				//check if this node is better
				if(node.getMaster() != null && (this.master == null || this.cost > node.getCost())){
					cheapestNodeUpdated = true;
					this.master = node.getMaster();
					this.direction = side;
					this.cost = node.getCost();
				}
			}
		}
		if(cheapestNodeUpdated){
			postNodeChanged();
		}
	}
	
	private void postNodeChanged(){
//		ServerLogger.debug("node has been changed: [side=%s, cost=%d, coords=(%d,%d,%d)]", direction, getCost(), x(), y(), z());
		for(BlockSide side : BlockSide.values()){
			int[] rel = side.getRelativeCoordinates(x(), y(), z());
			TileEntity tile = entity.worldObj.getBlockTileEntity(rel[0], rel[1], rel[2]);			
			if(tile != null && tile instanceof INetworkNode){
				((INetworkNode) tile).onNodeChanged(side.opposite());
			}
		}
		entity.worldObj.markBlockForUpdate(x(), y(), z());
	}
}
