package nl.besuikerd.core.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerBesu extends Container{
	
	public void bindEntity(TileEntityInventory inventory, EntityPlayer player){
		for(int i = 0 ; i < inventory.inventory.stacks.size() ; i++){
			InventoryStackBesu stack = inventory.inventory.stacks.get(i);
			addSlotToContainer(new SlotBesu(inventory.inventory, i));
		}
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int slotIndex) {
	    Slot slot = (Slot) inventorySlots.get(slotIndex);
	    ItemStack stack = slot.getStack();
	    
	    if(stack == null){
	    	return null;
	    }
	    
	    ItemStack oldStack = stack.copy();
	    if(!tryMerge(stack, slotIndex)){
	    	return null;
	    }
	    if(stack.stackSize == 0){
	    	
	    	slot.putStack(null);
	    }
	    
	    if(oldStack.stackSize != stack.stackSize){
	    	return null;
	    }
	    
	    slot.onPickupFromSlot(par1EntityPlayer, stack);
	    
	    return stack;
	}
	
	/**
	 * try to merge the given stack somewhere in the inventory
	 * @param stack
	 * @param slotIndex
	 * @return
	 */
	protected boolean tryMerge(ItemStack stack, int slotIndex){
		return mergeItemStack(stack, 0, inventorySlots.size(), false);
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return true;
	}
}
