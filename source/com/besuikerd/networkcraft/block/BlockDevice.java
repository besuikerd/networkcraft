package com.besuikerd.networkcraft.block;

import com.besuikerd.core.block.BlockContainerBesu;
import com.besuikerd.core.block.MaterialBesu;
import com.besuikerd.networkcraft.NCIconRegister;

import net.minecraft.block.Block;
import net.minecraft.util.Icon;

public abstract class BlockDevice extends BlockContainerBesu{
	
	protected Icon icon_device;
	
	public BlockDevice(int id) {
		super(id, MaterialBesu.material_device);
		setStepSound(Block.soundMetalFootstep);
		appendUnlocalizedName("device");
	}
	
	@Override
	public Icon getIcon(int side, int meta) {
		return icon_device;
	}
	
	@Override
	public void registerIcons(NCIconRegister reg) {
		icon_device = reg.registerIcon("device");
	}	
}