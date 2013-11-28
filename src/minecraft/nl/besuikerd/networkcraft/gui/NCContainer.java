package nl.besuikerd.networkcraft.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import nl.besuikerd.networkcraft.core.inventory.IFakeSlot;

public abstract class NCContainer extends Container{
	
	protected TileEntity tileEntity;
	
	public NCContainer() {
		super();
	}
	
	@Override
	public ItemStack slotClick(int index, int mouseButton, int modifier, EntityPlayer player) {
		Slot slot = (Slot) inventorySlots.get(index);
		return slot instanceof IFakeSlot ? slotClickFake(slot, mouseButton, modifier, player) : slotClick(slot, mouseButton, modifier, player); 
	}
	
	public ItemStack slotClick(Slot s, int mouseButton, int modifier, EntityPlayer player){
		return null;
	}
	
	public ItemStack slotClickFake(Slot s, int mouseButton, int modifier, EntityPlayer player){
		return null;
	}
}
