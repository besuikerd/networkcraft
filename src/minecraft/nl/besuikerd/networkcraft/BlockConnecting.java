package nl.besuikerd.networkcraft;

import java.util.Arrays;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import nl.besuikerd.core.BlockSide;
import nl.besuikerd.core.BLogger;

import org.lwjgl.opengl.GL11;

public abstract class BlockConnecting extends BlockNetworkContainer {
	
	public BlockConnecting(int id, Material material) {
		super(id, material);
		this.isBlockContainer = true;
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
	public int getRenderType() {
		return -1;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	public boolean connectsTo(TileEntity other){
		return false;
	}
	
	public abstract void renderConnection(World world, TileEntity other, int x, int y, int z, BlockSide side);
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int blockId) {
		super.onNeighborBlockChange(world, x, y, z, blockId);
		BLogger.debug("neighbour change! %b", world.isRemote);
		updateNeighborConnections(world, x, y, z);
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z,
			EntityLivingBase entity, ItemStack stack) {
		super.onBlockPlacedBy(world, x, y, z, entity, stack);
		BLogger.debug("block added! %b", world.isRemote);
		updateNeighborConnections(world, x, y, z);
		world.markBlockForUpdate(x, y, z);
		for(BlockSide side : BlockSide.values()){
			updateNeighbor(world, side, x, y, z);
		}
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, int id, int meta) {
		super.breakBlock(world, x, y, z, id, meta);
		BLogger.debug("block broken! %b", world.isRemote);
		//update neighbor blocks
		for(BlockSide side : BlockSide.values()){
			updateNeighbor(world, side, x, y, z);
		}
	}
	
	
	private void updateNeighbor(World world, BlockSide side, int x, int y, int z){
		int[] rel = side.getRelativeCoordinates(x, y, z);
		TileEntity neigh = (TileEntity) world.getBlockTileEntity(rel[0], rel[1], rel[2]);
		if(neigh != null && neigh instanceof TileEntityConnecting){
			TileEntityConnecting entity = (TileEntityConnecting) neigh;
			TileEntity self = world.getBlockTileEntity(x, y, z);
			entity.getConnectedSides()[side.opposite().ordinal()] = connectsTo(self);
		}
		world.markBlockForUpdate(rel[0], rel[1], rel[2]);
	}
	
	private void updateNeighborConnections(World world, int x, int y, int z){
		TileEntity e = (TileEntity) world.getBlockTileEntity(x, y, z);
		if(e != null && e instanceof TileEntityConnecting){
			TileEntityConnecting entity = (TileEntityConnecting) e;
			for(BlockSide b : BlockSide.values()){
				int[] rel = b.getRelativeCoordinates(x, y, z);
				TileEntity neigh = world.getBlockTileEntity(rel[0], rel[1], rel[2]);
				entity.getConnectedSides()[b.ordinal()] = neigh != null && connectsTo(neigh);
				world.markBlockForUpdate(rel[0], rel[1], rel[2]);
			}
		}
	}
}
