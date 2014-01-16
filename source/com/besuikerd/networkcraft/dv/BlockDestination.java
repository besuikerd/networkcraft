package com.besuikerd.networkcraft.dv;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.besuikerd.core.block.BlockContainerBesu;

public class BlockDestination extends BlockContainerBesu{
	
	public BlockDestination(int id, Material material) {
		super(id, material);
		setTextureName("noteblock");
		setUnlocalizedName("destination");
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityDestination();
	}
}
