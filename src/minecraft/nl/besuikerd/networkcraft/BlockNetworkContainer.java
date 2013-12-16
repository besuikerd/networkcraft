package nl.besuikerd.networkcraft;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class BlockNetworkContainer extends BlockNetwork implements ITileEntityProvider{
	
	public BlockNetworkContainer(int par1, Material par2Material) {
		super(par1, par2Material);
		this.isBlockContainer = true;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z,
			int id, int meta) {
		// TODO Auto-generated method stub
		super.breakBlock(world, x, y, z, id, meta);
		world.removeBlockTileEntity(x, y, z);
	}
	
	@Override
	public boolean onBlockEventReceived(World world, int x, int y,
			int z, int blockId, int eventId) {
		super.onBlockEventReceived(world, x, y, z, blockId, eventId);
		TileEntity entity = world.getBlockTileEntity(x, y, z);
		return entity == null ? false : entity.receiveClientEvent(blockId, eventId);
	}
}
