package nl.besuikerd.networkcraft.graph;

import net.minecraft.tileentity.TileEntity;
import nl.besuikerd.core.BlockSide;
import nl.besuikerd.core.utils.MathUtils;

public abstract class NetworkNodeInitializerBase<E extends INetworkNode> implements INetworkNode{
	protected E instance;
	protected Class<E> instanceClass;
	
	private TileEntity entity;

	private int x;
	private int y;
	private int z;

	public NetworkNodeInitializerBase(TileEntity entity, int x, int y, int z) {
		this.entity = entity;
		this.x = x;
		this.y = y;
		this.z = z;
		this.instanceClass = getClassInstance();
	}
	
	public abstract Class<E> getClassInstance();
	
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
		if (instance != null) {
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
	
	protected void checkExists() {
		if (instance == null) {
			TileEntity tile = entity.worldObj.getBlockTileEntity(x, y, z);
			if (tile != null && instanceClass.isInstance(tile)) {
				instance = instanceClass.cast(tile);
			}
		}
	}
	
	@Override
	public int hashCode() {
		return MathUtils.sum(x(), y(), z());
	}
}
