package nl.besuikerd.networkcraft.generic;

import net.minecraft.block.BlockEndPortal;
import net.minecraft.block.BlockEndPortalFrame;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.BlockRedstoneLogic;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.client.renderer.tileentity.TileEntityMobSpawnerRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityEndPortal;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import nl.besuikerd.networkcraft.core.BlockSide;
import nl.besuikerd.networkcraft.core.NCIconRegister;

public class BlockRouter extends BlockDevice{
	
	protected Icon icon_router_top;
	protected Icon icon_router_bottom;
	protected Icon icon_router_front;

	public BlockRouter(int id) {
		super(id);
		appendUnlocalizedName("router");
	}
	
	@Override
	public void registerIcons(NCIconRegister reg) {
		super.registerIcons(reg);
		icon_router_top = reg.registerIcon("router_top");
		icon_router_bottom = reg.registerIcon("router_bottom");
		
		icon_router_front = reg.registerIcon("router_front");
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
}
