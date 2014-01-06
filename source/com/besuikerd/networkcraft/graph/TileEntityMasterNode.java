package com.besuikerd.networkcraft.graph;

import java.util.Map;
import java.util.Set;

import com.besuikerd.core.BlockSide;
import com.besuikerd.core.tileentity.TileEntityBesu;
import com.besuikerd.core.utils.NBTUtils;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileEntityMasterNode extends TileEntityBesu implements IMasterNode{
	
	public static final String TAG_NODE = "node";
	
	protected MasterNode node;
	
	public TileEntityMasterNode(){
		this.node = new MasterNode(this, 5);
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
		//nothing to do here =)
	}
	
	@Override
	public void onTileEntityPlacedBy(World world, int x, int y, int z,
			EntityLivingBase entity, ItemStack stack) {
		super.onTileEntityPlacedBy(world, x, y, z, entity, stack);
		postNodeChanged();
		node.onPlaced();
	}

	@Override
	public void onRemoveTileEntity(World world, int x, int y, int z) {
		super.onRemoveTileEntity(world, x, y, z);
		node.onDestroyed();
		postNodeChanged();
		postNodeChanged();
	}
	
	@Override
	public void onTileEntityRemoved(World world, int x, int y, int z) {
		super.onTileEntityRemoved(world, x, y, z);
		node.onPostDestroyed();
	}
	
	@Override
	public int x() {
		return node.x();
	}
	
	@Override
	public int y() {
		return node.y();
	}
	
	@Override
	public int z() {
		return node.z();
	}
	
	@Override
	public void register(IEndPoint endPoint) {
		node.register(endPoint); 
	}

	@Override
	public void unregister(IEndPoint endPoint) {
		node.register(endPoint);
	}
	

	@Override
	public Set<IEndPoint> registeredEndPoints() {
		return node.registeredEndPoints();
	}

	@Override
	public Map<IEndPoint, Integer> endPoints() {
		return node.endPoints();
	}

	@Override
	public Map<IMasterNode, Integer> getConnectedMasters() {
		return node.getConnectedMasters();
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

	@Override
	public void invalidateConnectedMasters() {
		node.invalidateConnectedMasters();
	}
	
	@Override
	public int hashCode() {
		return node.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		return node.equals(obj);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		NBTUtils.readProcessData(node, tag, TAG_NODE);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		NBTUtils.writeProcessData(node, tag, TAG_NODE);
	}

	@Override
	public void invalidateEndPoints() {
		node.invalidateEndPoints();
	}
}
