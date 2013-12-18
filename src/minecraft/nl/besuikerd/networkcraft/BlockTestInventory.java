package nl.besuikerd.networkcraft;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import nl.besuikerd.core.inventory.TileEntityTestInventory;
import nl.besuikerd.gui.GuiId;

public class BlockTestInventory extends BlockDevice{

	public BlockTestInventory(int id) {
		super(id);
		appendUnlocalizedName("testinventory");
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityTestInventory();
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer player, int unknown, float aX, float aY, float aZ) {
		player.openGui(NetworkCraft.instance, GuiId.INVENTORYTEST.getNumber(), world, x, y, z);
		return true;
	}
}
