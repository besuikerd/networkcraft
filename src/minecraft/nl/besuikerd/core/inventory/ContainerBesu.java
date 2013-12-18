package nl.besuikerd.core.inventory;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import nl.besuikerd.core.BLogger;

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
	    if(!mergeItemStack(stack, 0, inventorySlots.size() - 1, true)){
	    	return null;
	    }
	    
	    if(stack.stackSize == 0){
	    	slot.putStack(null);
	    	slot.onSlotChanged();
	    }
	    if(oldStack.stackSize != stack.stackSize){
	    	return null;
	    }
	    slot.onPickupFromSlot(par1EntityPlayer, stack);
	    
	    return stack;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return true;
	}
}
