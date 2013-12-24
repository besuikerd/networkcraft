package nl.besuikerd.networkcraft.block;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import nl.besuikerd.core.ServerLogger;
import nl.besuikerd.networkcraft.NCIconRegister;
import nl.besuikerd.networkcraft.graph.INetworkNode;
import nl.besuikerd.networkcraft.graph.TileEntityEndPoint;

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
	public void registerIcons(NCIconRegister reg) {
		super.registerIcons(reg);
		this.icon_ep = reg.registerIcon("device_ep");
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
