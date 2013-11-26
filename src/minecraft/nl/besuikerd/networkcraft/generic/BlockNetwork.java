package nl.besuikerd.networkcraft.generic;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import nl.besuikerd.networkcraft.core.BlockSide;
import nl.besuikerd.networkcraft.core.NCLogger;
import nl.besuikerd.networkcraft.core.NCIconRegister;

public class BlockNetwork extends Block{
	
	public BlockNetwork(int par1, Material par2Material) {
		super(par1, par2Material);
		setUnlocalizedName("nc");
	}
	
	@Override
	public final void registerIcons(IconRegister reg) {
		registerIcons(new NCIconRegister(reg));
	}
	
	public void registerIcons(NCIconRegister reg){
	}
	
	public String appendUnlocalizedName(String toAppend){
		String newName = String.format("%s.%s", getUnlocalizedName().substring("tile.".length()), toAppend);
		setUnlocalizedName(newName);
		return newName;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z,
			EntityLivingBase entity, ItemStack stack) {
		int side = -1;
		if (MathHelper.abs((float)entity.posX - (float)x) < 2.0F && MathHelper.abs((float)entity.posZ - (float)z) < 2.0F) {
            double d0 = entity.posY + 1.82D - (double)entity.yOffset;
            if (d0 - (double)y > 2.0D) {
                side = 1;
            }
            if ((double)y - d0 > 0.0D) {
                side = 0;
            }
        }
        int l = MathHelper.floor_double((double)(entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        int direction = l == 0 ? 2 : (l == 1 ? 5 : (l == 2 ? 3 : (l == 3 ? 4 : 0)));
        side = side != -1 ? side : direction;
		onBlockPlacedPositioned(world, x, y, z, BlockSide.lookup(side), BlockSide.lookup(direction), stack);
	}
	
	public void onBlockPlacedPositioned(World world, int x, int y, int z, BlockSide side, BlockSide direction, ItemStack stack){
		if(world.isRemote) NCLogger.debug("block placed with side: %s and direction: %s", side, direction);
	}
}
