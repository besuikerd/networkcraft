package nl.besuikerd.core.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerBesuWithPlayerInventory extends ContainerBesu{
	@Override
	public void bindEntity(TileEntityInventory inventory, EntityPlayer player) {
		
		//attach player inventory
		for(int i = 0 ; i < 36 ; i++){
			Slot slot = new Slot(player.inventory, i, 0, 0);
			addSlotToContainer(slot);
		}
		super.bindEntity(inventory, player);
	}
	
	@Override
	protected boolean tryMerge(ItemStack stack, int slotIndex) {
		return slotIndex < 36 && mergeItemStack(stack, 36, inventorySlots.size(), false) || slotIndex >= 36 && mergeItemStack(stack, 0, 36, false);
	}
}
