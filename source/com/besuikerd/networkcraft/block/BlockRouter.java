package com.besuikerd.networkcraft.block;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

import com.besuikerd.core.BlockSide;

public class BlockRouter extends BlockDevice{
	
	protected Icon icon_router_top;
	protected Icon icon_router_bottom;
	protected Icon icon_router_front;

	public BlockRouter(int id) {
		super(id);
		appendUnlocalizedName("router");
	}
	
	@Override
	public void registerIcons(IconRegister reg) {
		super.registerIcons(reg);
		icon_router_top = reg.registerIcon("networkcraft:router_top");
		icon_router_bottom = reg.registerIcon("networkcraft:router_bottom");
		icon_router_front = reg.registerIcon("networkcraft:router_front");
	}
	

	@Override
	public void onBlockPlacedPositioned(World world, int x, int y, int z,
			BlockSide side, BlockSide direction, ItemStack stack) {
		super.onBlockPlacedPositioned(world, x, y, z, side, direction, stack);
		world.setBlockMetadataWithNotify(x, y, z, direction.ordinal(), 2);
	}

	@Override
	public Icon getIcon(int side, int meta) {
		switch(BlockSide.lookup(side)){
		case TOP:
			return icon_router_top;
		case BOTTOM:
			return icon_router_bottom;
		default:
			return side == meta ? icon_router_front : super.getIcon(side, meta);
		}
	}
	
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntity();
	}
}
