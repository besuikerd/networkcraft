package com.besuikerd.core.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * container that has automated container logic for a
 * {@link TileEntityInventory}. This container will be bound to a
 * TileEntityInventory upon creation from which it can extract information
 * regarding the inventory slots that are stored in the TileEntity.
 * 
 * @author Besuikerd
 * 
 */
public class ContainerBesu extends Container {

	/**
	 * binds the given TileEntity to this container, from which it will extract
	 * information regarding inventory slots
	 * 
	 * @param tile
	 * @param player
	 */
	public void bindEntity(TileEntityInventory tile, EntityPlayer player) {
		for (int i = 0; i < tile.inventory.stacks.size(); i++) {
			InventoryStackBesu stack = tile.inventory.stacks.get(i);
			addSlotToContainer(new SlotBesu(tile.inventory, i));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int slotIndex) {
		Slot slot = (Slot) inventorySlots.get(slotIndex);
		ItemStack stack = slot.getStack();

		if (stack == null) {
			return null;
		}

		ItemStack oldStack = stack.copy();
		if (!tryMerge(stack, slotIndex)) {
			return null;
		}
		if (stack.stackSize == 0) {

			slot.putStack(null);
		}

		if (oldStack.stackSize != stack.stackSize) {
			return null;
		}

		slot.onPickupFromSlot(par1EntityPlayer, stack);

		return stack;
	}

	/**
	 * try to merge the given stack somewhere in the inventory
	 * 
	 * @param stack
	 * @param slotIndex
	 * @return
	 */
	protected boolean tryMerge(ItemStack stack, int slotIndex) {
		return mergeItemStack(stack, 0, inventorySlots.size(), false);
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return true;
	}
}
