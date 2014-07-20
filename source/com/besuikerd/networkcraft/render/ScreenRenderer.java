package com.besuikerd.networkcraft.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import com.besuikerd.logging.BLogger;
import com.besuikerd.render.ISimpleBlockRenderingHandlerBase;
import com.besuikerd.utils.BlockSide;

public class ScreenRenderer extends ISimpleBlockRenderingHandlerBase{

	private int meta;
	
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		this.meta = world.getBlockMetadata(x, y, z);
		renderStandardBlock(world, block, x, y, z, renderer);
		BLogger.debug("meta: %d", meta);
		return true;
	}
	
	@Override
	public void renderFaceTop(Block block, int x, int y, int z, IIcon icon, RenderBlocks renderer) {
		switch(BlockSide.values()[meta]){
		case NORTH: renderFaceTopRotated(block, x, y, z, icon, renderer, R90);
			break;
		case EAST: renderFaceTopRotated(block, x, y, z, icon, renderer, R180);
			break;
		case SOUTH: renderFaceTopRotated(block, x, y, z, icon, renderer, R270);
			break;
		default:
			super.renderFaceTop(block, x, y, z, icon, renderer);
			break;
		}
	}
	
	@Override
	public void renderFaceBottom(Block block, int x, int y, int z, IIcon icon, RenderBlocks renderer) {
		switch(BlockSide.values()[meta]){
		case NORTH: renderFaceBottomRotated(block, x, y, z, icon, renderer, R270);
			break;
		case EAST: renderFaceBottomRotated(block, x, y, z, icon, renderer, R180);
			break;
		case SOUTH: renderFaceBottomRotated(block, x, y, z, icon, renderer, R90);
			break;
		default:
			super.renderFaceBottom(block, x, y, z, icon, renderer);
			break;
		}
	}
	
	@Override
	public void renderFaceNorth(Block block, int x, int y, int z, IIcon icon, RenderBlocks renderer) {
		switch(BlockSide.values()[meta]){
		case TOP: renderFaceNorthRotated(block, x, y, z, icon, renderer, R270);
			break;
		case BOTTOM: renderFaceNorthRotated(block, x, y, z, icon, renderer, R90);
			break;
		case EAST: renderFaceNorthRotated(block, x, y, z, icon, renderer, R180);
			break;
		default:
			super.renderFaceNorth(block, x, y, z, icon, renderer);
			break;
		}
	}
	
	@Override
	public void renderFaceEast(Block block, int x, int y, int z, IIcon icon, RenderBlocks renderer) {
		switch(BlockSide.values()[meta]){
		case TOP: renderFaceEastRotated(block, x, y, z, icon, renderer, R270);
			break;
		case BOTTOM: renderFaceEastRotated(block, x, y, z, icon, renderer, R90);
			break;
		case SOUTH: renderFaceEastRotated(block, x, y, z, icon, renderer, R180);
			break;
		default:
			super.renderFaceEast(block, x, y, z, icon, renderer);
			break;
		}
	}
	
	@Override
	public void renderFaceSouth(Block block, int x, int y, int z, IIcon icon, RenderBlocks renderer) {
		switch(BlockSide.values()[meta]){
		case TOP: renderFaceSouthRotated(block, x, y, z, icon, renderer, R90);
			break;
		case BOTTOM: renderFaceSouthRotated(block, x, y, z, icon, renderer, R270);
			break;
		case EAST: renderFaceSouthRotated(block, x, y, z, icon, renderer, R180);
			break;
		default:
			super.renderFaceSouth(block, x, y, z, icon, renderer);
			break;
		}
	}
	
	@Override
	public void renderFaceWest(Block block, int x, int y, int z, IIcon icon, RenderBlocks renderer) {
		switch(BlockSide.values()[meta]){
		case TOP: renderFaceWestRotated(block, x, y, z, icon, renderer, R90);
			break;
		case BOTTOM: renderFaceWestRotated(block, x, y, z, icon, renderer, R270);
			break;
		case SOUTH: renderFaceWestRotated(block, x, y, z, icon, renderer, R180);
			break;
		default:
			super.renderFaceWest(block, x, y, z, icon, renderer);
			break;
		}
	}
	
	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return false;
	}
}
