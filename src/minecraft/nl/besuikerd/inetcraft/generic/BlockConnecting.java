package nl.besuikerd.inetcraft.generic;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

public abstract class BlockConnecting extends BlockINC{

	protected Block[] neighbours;
	
	public BlockConnecting(int id, Material material) {
		super(id, material);
		neighbours = new Block[6];
	}
	
	public abstract boolean connectsTo(Block other);
	
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int blockId) {
		super.onNeighborBlockChange(world, x, y, z, blockId);
		boolean valid = true;
		for(int i = 0 ; i < neighbours.length ; i++){
			
		}
	}
	
	/**
	 * call when the block configuration is invalidated and block needs to re render
	 */
	public void invalidateRender(){
		
	}
	
	public void onBlockConnected(){
		invalidateRender();
	}
	
	public void onBlockDisconnected(){
		invalidateRender();
	}
}
