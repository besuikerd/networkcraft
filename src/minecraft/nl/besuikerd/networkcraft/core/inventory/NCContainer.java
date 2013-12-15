package nl.besuikerd.networkcraft.core.inventory;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import nl.besuikerd.networkcraft.core.NCLogger;

public class NCContainer extends Container{

	protected List<Slot> playerSlots;
	protected TileEntityInventory entity;
	
	public NCContainer() {
		
	}
	
	public void bindEntity(TileEntityInventory inventory, EntityPlayer player){
		this.entity = inventory;
		for(NCSlot s : inventory.slots){
			addSlotToContainer(s);
		}
		
		
		if(inventory.hasPlayerInventory()){
			playerSlots = new ArrayList<Slot>();
			for(int i = 0 ; i < 36 ; i++){
				Slot slot = new Slot(player.inventory, i, 0, 0);
				playerSlots.add(slot);
				addSlotToContainer(slot);
			}
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
	    if(entity.hasPlayerInventory()){
	    	if(slotIndex < 9){
	    		if(!mergeItemStack(stack, 9, 35, false)){
	    			return null;
	    		}
	    	} else if(slotIndex < 36){
	    		if(!mergeItemStack(stack, 0, 8, false)){
	    			return null;
	    		}
	    	}
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
	
	public List<Slot> getPlayerSlots() {
		return playerSlots;
	}
}
