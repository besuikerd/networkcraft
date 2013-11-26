package nl.besuikerd.networkcraft.generic;

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
import nl.besuikerd.networkcraft.core.BlockSide;
import nl.besuikerd.networkcraft.core.NCIconRegister;

public class BlockCable extends BlockConnecting{

	public BlockCable(int id) {
		super(id, Material.rock);
		setHardness(2f);
		setUnlocalizedName("cable");
		setCreativeTab(CreativeTabs.tabMisc);
		setStepSound(Block.soundMetalFootstep);
	}
	
	@Override
	public boolean connectsTo(TileEntity other) {
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityBlockCable(connectedSides);
	}
	
	@Override
	public void registerIcons(NCIconRegister reg) {
		this.blockIcon = reg.registerIcon("cable");
	}

	@Override
	public void renderConnection(World world, TileEntity other, int x, int y,
			int z, BlockSide side) {
		
	}
}
