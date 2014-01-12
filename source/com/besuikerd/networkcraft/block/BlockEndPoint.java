package com.besuikerd.networkcraft.block;

import com.besuikerd.core.ServerLogger;
import com.besuikerd.networkcraft.NCIconRegister;
import com.besuikerd.networkcraft.graph.INetworkNode;
import com.besuikerd.networkcraft.graph.TileEntityEndPoint;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockEndPoint extends BlockDevice{
	
	public BlockEndPoint(int id) {
		super(id);
		setUnlocalizedName("endpoint");
	}

	protected Icon icon_ep;
	
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityEndPoint();
	}
	
	@Override
	public void registerIcons(IconRegister reg) {
		super.registerIcons(reg);
		this.icon_ep = reg.registerIcon("networkcraft:device_ep");
	}
	
	public Icon getIcon(int side, int meta) {
		return icon_ep;
	};
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer player, int unknown, float aX, float aY, float aZ) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if(tile != null && tile instanceof INetworkNode){
			INetworkNode node = (INetworkNode) tile;
			if(world.isRemote) player.addChatMessage(String.format("Cable[side=%s, cost=%d, coords=(%d,%d,%d), master=%b]", node.getDirection(), node.getCost(), node.x(), node.y(), node.z(), node.getMaster() != null));
			ServerLogger.debug("Cable[side=%s, cost=%d, coords=(%d,%d,%d), master=%s]", node.getDirection(), node.getCost(), node.x(), node.y(), node.z(), node.getMaster());
		}
		return true;
	}

}
