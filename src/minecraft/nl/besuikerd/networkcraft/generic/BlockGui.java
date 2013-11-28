package nl.besuikerd.networkcraft.generic;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import nl.besuikerd.networkcraft.core.NCLogger;
import nl.besuikerd.networkcraft.gui.NCGuiId;

public class BlockGui extends BlockDevice{

	public BlockGui(int id) {
		super(id);
		appendUnlocalizedName("gui");
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer player, int unknown, float aX, float aY, float aZ) {
		player.openGui(NetworkCraft.instance, NCGuiId.TEST.getNumber(), world, x, y, z);
		return super.onBlockActivated(world, x, y, z, player, unknown, aX, aY, aZ);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntity();
	}
	
}
