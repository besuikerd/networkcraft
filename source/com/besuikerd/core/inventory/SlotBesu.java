package com.besuikerd.core.inventory;

import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotBesu extends Slot {
	
	protected InventoryBesu inventory;
	
	public SlotBesu(InventoryBesu inventory, int slotIndex, int xDisplay, int yDisplay){
		super(inventory, slotIndex, xDisplay, yDisplay);
		this.inventory = inventory;
	}
	
	public SlotBesu(InventoryBesu inventory, int slotIndex){
		this(inventory, slotIndex, 0, 0);
	}
	
	/**
	 * attempt to add the contents of the other stack to this slot
	 * @param other
	 * @return amount of items added to this stack
	 */
	public int merge(ItemStack other){
		ItemStack stack = getStack();
		if(stack.isItemEqual(other)){
			int itemsMergable = stack.getMaxStackSize() - stack.stackSize;
			stack.stackSize += itemsMergable;
			return itemsMergable;
		}
		return 0;
	}

	@Override
	public void onSlotChanged() {
		inventory.onInventoryChanged();
	}
}
