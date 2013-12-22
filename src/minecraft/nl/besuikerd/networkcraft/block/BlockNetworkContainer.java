package nl.besuikerd.networkcraft.block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import nl.besuikerd.core.ServerLogger;
import nl.besuikerd.networkcraft.tileentity.TileEntityBesu;

public abstract class BlockNetworkContainer extends BlockNetwork implements ITileEntityProvider{
	
	public BlockNetworkContainer(int par1, Material par2Material) {
		super(par1, par2Material);
		this.isBlockContainer = true;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z,
			int id, int meta) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if(tile != null && tile instanceof TileEntityBesu){
			((TileEntityBesu) tile).onRemoveTileEntity(world, x, y, z);
		}
		super.breakBlock(world, x, y, z, id, meta);
		world.removeBlockTileEntity(x, y, z);
		if(tile != null && tile instanceof TileEntityBesu){
			((TileEntityBesu) tile).onTileEntityRemoved(world, x, y, z);
		}
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z,
			EntityLivingBase entity, ItemStack stack) {
		super.onBlockPlacedBy(world, x, y, z, entity, stack);
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if(tile != null && tile instanceof TileEntityBesu){
			((TileEntityBesu) tile).onTileEntityPlacedBy(world, x, y, z, entity, stack);
		}
	}
	
	
	
	@Override
	public boolean onBlockEventReceived(World world, int x, int y,
			int z, int blockId, int eventId) {
		super.onBlockEventReceived(world, x, y, z, blockId, eventId);
		TileEntity entity = world.getBlockTileEntity(x, y, z);
		return entity == null ? false : entity.receiveClientEvent(blockId, eventId);
	}
}
