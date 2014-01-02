package nl.besuikerd.networkcraft.graph;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import nl.besuikerd.core.BlockSide;
import nl.besuikerd.core.tileentity.TileEntityBesu;
import nl.besuikerd.core.utils.NBTUtils;

public class TileEntityNetworkNode extends TileEntityBesu implements INetworkNode{

	public static final String TAG_NODE = "node";
	
	protected NetworkNode node;
	
	public TileEntityNetworkNode() {
		node = new NetworkNode(this, 1);
	}
	
	@Override
	public IMasterNode getMaster() {
		return node.getMaster();
	}

	@Override
	public int getNodeCost() {
		return node.getNodeCost();
	}
	
	@Override
	public int getCost() {
		return node.getCost();
	}
	
	@Override
	public BlockSide getDirection() {
		return node.getDirection();
	}
	
	@Override
	public void onNodeChanged(BlockSide side) {
		node.onNodeChanged(side);
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
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		NBTUtils.writeProcessData(node, tag, TAG_NODE);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		NBTUtils.readProcessData(node, tag, TAG_NODE);
	}

	@Override
	public void onTileEntityPlacedBy(World world, int x, int y, int z,
			EntityLivingBase entity, ItemStack stack) {
		super.onTileEntityPlacedBy(world, x, y, z, entity, stack);
		node.onPlaced();
	}
	
	@Override
	public void onRemoveTileEntity(World world, int x, int y, int z) {
		super.onRemoveTileEntity(world, x, y, z);
		node.onDestroyed();
	}
	
	@Override
	public void onTileEntityRemoved(World world, int x, int y, int z) {
		node.onPostDestroyed();
	}
}
