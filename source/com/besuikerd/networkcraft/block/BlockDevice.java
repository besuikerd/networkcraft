package com.besuikerd.networkcraft.block;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;

import com.besuikerd.core.block.BlockContainerBesu;
import com.besuikerd.core.block.MaterialBesu;

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
	public void registerIcons(IconRegister reg) {
		icon_device = reg.registerIcon("networkcraft:device");
	}	
}