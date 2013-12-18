package nl.besuikerd.core.inventory;

import java.io.IOException;
import java.util.Arrays;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import nl.besuikerd.core.BlockSide;
import nl.besuikerd.core.BLogger;
import nl.besuikerd.core.packet.IProcessData;
import nl.besuikerd.core.utils.BitUtils;

public class SlotBesu extends Slot {
	
	protected InventoryBesu inventory;
	
	public SlotBesu(InventoryBesu inventory, int slotIndex, int xDisplay, int yDisplay){
		super(inventory, slotIndex, xDisplay, yDisplay);
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

}
