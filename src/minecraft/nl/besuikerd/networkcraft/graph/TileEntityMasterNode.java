package nl.besuikerd.networkcraft.graph;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import nl.besuikerd.core.BLogger;
import nl.besuikerd.core.BlockSide;
import nl.besuikerd.core.ServerLogger;
import nl.besuikerd.networkcraft.tileentity.TileEntityBesu;

public class TileEntityMasterNode extends TileEntityBesu implements IMasterNode{

	private boolean destroyed = false;
	
	public TileEntityMasterNode(){
	}
	
	@Override
	public IMasterNode getMaster() {
		return destroyed ? null : this;
	}
	
	@Override
	public int getNodeCost() {
		return 0;
	}
	
	@Override
	public int getCost() {
		return destroyed ? Integer.MAX_VALUE : 0;
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
	public void onTileEntityPlacedBy(World world, int x, int y, int z,
			EntityLivingBase entity, ItemStack stack) {
		super.onTileEntityPlacedBy(world, x, y, z, entity, stack);
		postNodeChanged();
	}

	@Override
	public void onRemoveTileEntity(World world, int x, int y, int z) {
		super.onRemoveTileEntity(world, x, y, z);
		destroyed = true;
		postNodeChanged();
		postNodeChanged();
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
	
	private void postNodeChanged(){
		for(BlockSide side : BlockSide.values()){
			int[] rel = side.getRelativeCoordinates(x(), y(), z());
			TileEntity tile = worldObj.getBlockTileEntity(rel[0], rel[1], rel[2]);			
			if(tile != null && tile instanceof INetworkNode){
				((INetworkNode) tile).onNodeChanged(side.opposite());
			}
		}
	}
}
