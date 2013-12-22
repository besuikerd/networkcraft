package nl.besuikerd.networkcraft.block;

import java.util.Random;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import nl.besuikerd.core.gui.GuiId;
import nl.besuikerd.networkcraft.NetworkCraft;
import nl.besuikerd.networkcraft.tileentity.TileEntityTestInventory;

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
	public void breakBlock(World world, int x, int y, int z, int id, int meta) {
		Random r = new Random();
		TileEntity entity = world.getBlockTileEntity(x, y, z);
		if(entity != null && entity instanceof IInventory){
			IInventory inv = (IInventory) entity;
			for(int i = 0 ; i < inv.getSizeInventory() ; i++){
				ItemStack stack = inv.getStackInSlot(i);
				if(stack != null && stack.stackSize > 0){
					EntityItem entityItem = new EntityItem(world, x + r.nextDouble() * 0.8 + 0.1, y + r.nextDouble() * 0.8 + 0.1, z + r.nextDouble() * 0.8 + 0.1, stack);
					entityItem.motionX = r.nextGaussian() * 0.05;
					entityItem.motionY = r.nextGaussian() * 0.05 + 0.2;
					entityItem.motionZ = r.nextGaussian() * 0.05;
					world.spawnEntityInWorld(entityItem);
				}
			}
		}
		super.breakBlock(world, x, y, z, id, meta);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer player, int unknown, float aX, float aY, float aZ) {
		player.openGui(NetworkCraft.instance, GuiId.INVENTORYTEST.getNumber(), world, x, y, z);
		return true;
	}
}
