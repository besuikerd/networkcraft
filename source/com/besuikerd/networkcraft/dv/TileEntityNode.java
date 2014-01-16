package com.besuikerd.networkcraft.dv;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.besuikerd.core.tileentity.TileEntityBesu;

public class TileEntityNode extends TileEntityBesu implements INode{
	
	protected Node node;
	
	public TileEntityNode() {
		node = new Node(this, 1);
	}
	
	@Override
	public void onTileEntityPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		super.onTileEntityPlacedBy(world, x, y, z, entity, stack);
		node.update(worldObj, x(), y(), z());
	}
	
	@Override
	public int cost() {
		return node.cost();
	}

	@Override
	public int compareTo(INode o) {
		return node.compareTo(o);
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
	public boolean equals(Object obj) {
		return node.equals(obj);
	}
	
	@Override
	public int hashCode() {
		return node.hashCode();
	}
	
	@Override
	public String toString() {
		return node.toString();
	}
}
