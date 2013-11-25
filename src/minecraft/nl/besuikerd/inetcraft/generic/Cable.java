package nl.besuikerd.inetcraft.generic;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;

public class Cable extends BlockContainer{

	public Cable(int par1, Material par2Material) {
		super(par1, par2Material);
		setHardness(2f);
		setUnlocalizedName("cable");
		setCreativeTab(CreativeTabs.tabMisc);
		setStepSound(Block.soundMetalFootstep);
	}
	
	@Override
	public boolean shouldSideBeRendered(IBlockAccess par1iBlockAccess,
			int par2, int par3, int par4, int par5) {
		return false;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new CableTileEntity();
	}
	
	@Override
	public void registerIcons(IconRegister reg) {
		reg.registerIcon("generic:cable");
	}
	
}
