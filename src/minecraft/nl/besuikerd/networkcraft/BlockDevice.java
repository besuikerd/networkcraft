package nl.besuikerd.networkcraft;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public abstract class BlockDevice extends BlockNetworkContainer{

	protected Icon icon_device;
	
	public BlockDevice(int id) {
		super(id, Material.iron);
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
