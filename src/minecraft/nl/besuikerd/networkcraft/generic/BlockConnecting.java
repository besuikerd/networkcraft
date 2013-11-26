package nl.besuikerd.networkcraft.generic;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import nl.besuikerd.networkcraft.core.BlockSide;

import org.lwjgl.opengl.GL11;

public abstract class BlockConnecting extends BlockNetworkContainer {
	
	protected final boolean[] connectedSides = new boolean[6];
	
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
	}
	
	@Override
	public int onBlockPlaced(World world, int x, int y, int z,
			int side, float hitX, float hitY, float hitZ, int meta) {
		super.onBlockPlaced(world, x, y, z, side, hitX, hitY, hitZ, meta);
		updateNeighborConnections(world, x, y, z);
		return meta;
	}
	
	
	
	private void updateNeighborConnections(World world, int x, int y, int z){
		for(BlockSide b : BlockSide.values()){
			int[] rel = b.getRelativeCoordinates(x, y, z);
			TileEntity entity = world.getBlockTileEntity(rel[0], rel[1], rel[2]);
			connectedSides[b.ordinal()] = entity != null && connectsTo(entity); 
		}
	}
	
	public boolean[] getConnectedSides() {
		return connectedSides;
	}
}
