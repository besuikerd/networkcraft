package nl.besuikerd.networkcraft.block;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import nl.besuikerd.core.BLogger;
import nl.besuikerd.core.gui.GuiId;
import nl.besuikerd.networkcraft.NetworkCraft;
import nl.besuikerd.networkcraft.tileentity.TileEntityTestGui;

public class BlockTestGui extends BlockDevice{

	public BlockTestGui(int id) {
		super(id);
		appendUnlocalizedName("gui");
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityTestGui();
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int unknown, float aX, float aY, float aZ) {
		player.openGui(NetworkCraft.instance, GuiId.GUITEST.getNumber(), world, x, y, z);
		return true;
	}

}
