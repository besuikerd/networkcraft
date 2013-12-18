package nl.besuikerd.networkcraft;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import nl.besuikerd.core.BLogger;
import nl.besuikerd.gui.GuiId;

public class BlockGui extends BlockDevice{

	public BlockGui(int id) {
		super(id);
		appendUnlocalizedName("gui");
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer player, int unknown, float aX, float aY, float aZ) {
		player.openGui(NetworkCraft.instance, GuiId.TEST.getNumber(), world, x, y, z);
		return super.onBlockActivated(world, x, y, z, player, unknown, aX, aY, aZ);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntity();
	}
	
}
