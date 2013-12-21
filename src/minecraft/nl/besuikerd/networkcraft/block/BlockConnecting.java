package nl.besuikerd.networkcraft.block;

import java.util.Arrays;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import nl.besuikerd.core.BLogger;
import nl.besuikerd.core.BlockSide;
import nl.besuikerd.networkcraft.tileentity.IConnectingSides;

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
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int blockId) {
		super.onNeighborBlockChange(world, x, y, z, blockId);
//		updateNeighborConnections(world, x, y, z);
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z,
			EntityLivingBase entity, ItemStack stack) {
		super.onBlockPlacedBy(world, x, y, z, entity, stack);
		TileEntity self = world.getBlockTileEntity(x, y, z);
		if(self != null && self instanceof IConnectingSides){
			((IConnectingSides) self).validateConnections();
			
			BLogger.debug("connecting sides: %s", Arrays.toString(((IConnectingSides) self).getConnectingSides()));
		}
		updateNeighbors(world, x, y, z);
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, int id, int meta) {
		super.breakBlock(world, x, y, z, id, meta);
		updateNeighbors(world, x, y, z);
	}
	
	private void updateNeighbors(World world, int x, int y, int z){
		for(BlockSide b : BlockSide.values()){
			int[] rel = b.getRelativeCoordinates(x, y, z);
			TileEntity other = world.getBlockTileEntity(rel[0], rel[1], rel[2]);
			if(other != null && other instanceof IConnectingSides){
				BLogger.debug("updating sides for neighbor %s", b);
				((IConnectingSides) other).validateConnections();
			}
		}
	}
	
//	private void updateNeighborConnections(World world, int x, int y, int z){
//		TileEntity e = (TileEntity) world.getBlockTileEntity(x, y, z);
//		if(e != null && e instanceof TileEntityConnecting){
//			TileEntityConnecting entity = (TileEntityConnecting) e;
//			for(BlockSide b : BlockSide.values()){
//				int[] rel = b.getRelativeCoordinates(x, y, z);
//				TileEntity neigh = world.getBlockTileEntity(rel[0], rel[1], rel[2]);
//				TileEntity self = world.getBlockTileEntity(x, y, z);
//				entity.getConnectedSides()[b.ordinal()] = neigh != null && self != null && self instanceof TileEntityConnecting && ((TileEntityConnecting) self).connectsTo(neigh);
//				world.markBlockForUpdate(rel[0], rel[1], rel[2]);
//			}
//		}
//	}
}
