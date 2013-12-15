package nl.besuikerd.networkcraft.generic;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import nl.besuikerd.networkcraft.core.inventory.TileEntityTestInventory;
import nl.besuikerd.networkcraft.gui.NCGuiId;

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
		player.openGui(NetworkCraft.instance, NCGuiId.INVENTORYTEST.getNumber(), world, x, y, z);
		return true;
	}
	
	
}
