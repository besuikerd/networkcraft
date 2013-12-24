package nl.besuikerd.networkcraft.block;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import nl.besuikerd.core.ServerLogger;
import nl.besuikerd.networkcraft.NCIconRegister;
import nl.besuikerd.networkcraft.graph.IMasterNode;
import nl.besuikerd.networkcraft.graph.TileEntityMasterNode;

public class BlockMasterNode extends BlockDevice{

	protected Icon icon_m;
	
	public BlockMasterNode(int id) {
		super(id);
		appendUnlocalizedName("master");
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityMasterNode();
	}
	
	@Override
	public void registerIcons(NCIconRegister reg) {
		super.registerIcons(reg);
		this.icon_m = reg.registerIcon("device_m");
	}
	
	@Override
	public Icon getIcon(int side, int meta) {
		return icon_m;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int unknown, float aX, float aY, float aZ) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if(tile != null && tile instanceof IMasterNode){
			IMasterNode node = (IMasterNode) tile;
			if(world.isRemote) player.addChatMessage(String.format("masters: %s, endpoints: %s", node.getConnectedMasters().values(), node.endPoints().values()));
			ServerLogger.debug("masters: %s, endpoints: %s", node.getConnectedMasters().values(), node.endPoints().values());
		}
		return true;
	}
}
