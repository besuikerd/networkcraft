package com.besuikerd.networkcraft.dv;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.besuikerd.core.block.BlockBesu;
import com.besuikerd.core.block.BlockContainerBesu;

public class BlockNode extends BlockContainerBesu{

	public BlockNode(int id, Material material) {
		super(id, material);
		setTextureName("sponge");
		setUnlocalizedName("node");
	}
	
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityNode();
	}
}
