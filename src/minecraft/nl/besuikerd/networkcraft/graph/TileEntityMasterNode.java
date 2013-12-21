package nl.besuikerd.networkcraft.graph;

import nl.besuikerd.core.BLogger;
import nl.besuikerd.core.BlockSide;
import nl.besuikerd.networkcraft.tileentity.TileEntityBesu;

public class TileEntityMasterNode extends TileEntityBesu implements IMasterNode{

	public TileEntityMasterNode(){
	}
	
	@Override
	public IMasterNode getMaster() {
		return this;
	}
	
	@Override
	public int getNodeCost() {
		return 0;
	}
	
	@Override
	public int getCost() {
		return 0;
	}
	
	@Override
	public BlockSide getDirection() {
		return null;
	}
	
	@Override
	public void onNodeChanged(BlockSide side) {
		//nothing to do here =)
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
	
	@Override
	public void register(IEndPoint child) {
		BLogger.debug("node registered %s[cost=%d, coords=(%d,%d,%d)]", child.getClass().getName(), child.getNodeCost(), child.x(), child.y(), child.z()); 
	}

	@Override
	public void unregister(IEndPoint child) {
		BLogger.debug("node unregistered %s[cost=%d, coords=(%d,%d,%d)]", child.getClass().getName(), child.getNodeCost(), child.x(), child.y(), child.z());
	}
	
}
