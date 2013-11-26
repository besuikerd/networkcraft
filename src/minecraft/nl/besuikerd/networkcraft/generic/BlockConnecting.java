package nl.besuikerd.networkcraft.generic;

import java.util.Arrays;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import nl.besuikerd.networkcraft.core.BlockSide;
import nl.besuikerd.networkcraft.core.NCLogger;

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
		updateNeighborConnections(world, x, y, z);
		NCLogger.debug("neighbour change! %b", ""+world.isRemote);
	}
	
	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);
		updateNeighborConnections(world, x, y, z);
		NCLogger.debug("block added! %b", ""+world.isRemote);
	}
	
	private void updateNeighborConnections(World world, int x, int y, int z){
		TileEntity e = (TileEntity) world.getBlockTileEntity(x, y, z);
		NCLogger.debug("entity: %s (%s)", e, e == null ? null : e.getClass());
		if(e != null && e instanceof TileEntityConnecting){
			TileEntityConnecting entity = (TileEntityConnecting) e;
			for(BlockSide b : BlockSide.values()){
				int[] rel = b.getRelativeCoordinates(x, y, z);
				TileEntity neigh = world.getBlockTileEntity(rel[0], rel[1], rel[2]);
				entity.getConnectedSides()[b.ordinal()] = connectsTo(neigh);
			}
		}
	}
	
}
