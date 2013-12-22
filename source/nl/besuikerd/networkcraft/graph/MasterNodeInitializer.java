package nl.besuikerd.networkcraft.graph;

import net.minecraft.tileentity.TileEntity;
import nl.besuikerd.core.BlockSide;

/**
 * wrapped around a TileEntity to simulate a reference to a masternode. Once one of it's methods is called, it will try to find the given TileEntity
 * @author Besuikerd
 *
 */
public class MasterNodeInitializer implements IMasterNode{
	private IMasterNode instance;
	private TileEntity entity;
	
	private int x;
	private int y;
	private int z;
	
	public MasterNodeInitializer(TileEntity entity, int x, int y, int z) {
		this.entity = entity;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@Override
	public IMasterNode getMaster() {
		checkExists();
		return instance == null ? null : instance.getMaster();
	}
	@Override
	public int getNodeCost() {
		checkExists();
		return instance == null ? 0 : instance.getNodeCost();
	}
	@Override
	public int getCost() {
		checkExists();
		return instance == null ? -1 : instance.getCost();
	}
	@Override
	public BlockSide getDirection() {
		checkExists();
		return instance == null ? null : instance.getDirection();
	}
	@Override
	public void onNodeChanged(BlockSide side) {
		checkExists();
		if(instance != null){
			instance.onNodeChanged(side);
		}
	}
	
	@Override
	public int x() {
		return x;
	}
	
	@Override
	public int y() {
		return y;
	}
	
	@Override
	public int z() {
		return z;
	}
	
	@Override
	public void register(IEndPoint endPoint) {
		checkExists();
		if(instance != null){
			instance.register(endPoint);
		}
	}

	@Override
	public void unregister(IEndPoint endPoint) {
		checkExists();
		if(instance != null){
			instance.unregister(endPoint);
		}
	}
	
	private void checkExists(){
		if(instance == null){
			TileEntity tile = entity.worldObj.getBlockTileEntity(x, y, z);
			if(tile != null && tile instanceof IMasterNode){
				instance = (IMasterNode) tile;
			}
		}
	}
}
