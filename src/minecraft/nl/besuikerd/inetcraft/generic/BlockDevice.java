package nl.besuikerd.inetcraft.generic;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import nl.besuikerd.inetcraft.core.NetworkCraftIconRegister;

public class BlockDevice extends BlockINC{

	protected Icon icon_device;
	
	public BlockDevice(int id) {
		super(id, Material.iron);
		setStepSound(Block.soundMetalFootstep);
		setUnlocalizedName("device");
	}
	
	@Override
	public Icon getIcon(int side, int meta) {
		return icon_device;
	}
	
	@Override
	public void registerIcons(NetworkCraftIconRegister reg) {
		icon_device = reg.registerIcon("device");
	}
}
