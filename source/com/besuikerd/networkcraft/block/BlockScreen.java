package com.besuikerd.networkcraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.besuikerd.block.BlockDirectional;
import com.besuikerd.logging.BLogger;
import com.besuikerd.networkcraft.ClientProxy;
import com.besuikerd.networkcraft.tileentity.TileEntityScreen;
import com.besuikerd.tileentity.TileEntityLogicConnecting;
import com.besuikerd.utils.BlockSide;
import com.besuikerd.utils.tuple.Vector3;

public class BlockScreen extends BlockDirectional{
	
	private IIcon icon_side;
	private IIcon icon_back;
	
	protected BlockScreen(Material material) {
		super(material);
	}
	

	@Override
	public void registerBlockIcons(IIconRegister register) {
		super.registerBlockIcons(register);
		icon_side = register.registerIcon("networkcraft:screen_side");
		icon_back = register.registerIcon("networkcraft:screen_back");
	}
	

	@Override
	public IIcon getIcon(int side, int meta) {
		return side == meta ? null : meta == ForgeDirection.OPPOSITES[side] ? icon_back : icon_side; 
	}
	
	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityScreen();
	}
	
	@Override
	public int getRenderType() {
		return ClientProxy.renderers().screen.getRenderId();
	}
	
	@Override
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
		Vector3 relative = BlockSide.values()[side].opposite().getRelativeCoordinates(x, y, z);
		int meta = world.getBlockMetadata(relative._1, relative._2, relative._3);
		return side != meta && super.shouldSideBeRendered(world, x, y, z, side);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int meta, float posX, float posY, float posZ) {
		BLogger.debug("%s", ((TileEntityScreen) world.getTileEntity(x, y, z)).getLogic(TileEntityLogicConnecting.class).getConnectingSides());
		return true;
	}
}
