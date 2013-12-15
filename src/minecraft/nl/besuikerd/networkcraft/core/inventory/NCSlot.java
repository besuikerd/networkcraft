package nl.besuikerd.networkcraft.core.inventory;

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
import nl.besuikerd.networkcraft.core.BlockSide;
import nl.besuikerd.networkcraft.core.NCLogger;
import nl.besuikerd.networkcraft.core.packet.IProcessData;
import nl.besuikerd.networkcraft.core.utils.BitUtils;

public class NCSlot extends Slot implements IProcessData{

	protected ItemStack stack;
	protected byte sides;
	
	public NCSlot(IInventory inventory, int slotIndex){
		this(inventory, null, slotIndex);
	}
	
	public NCSlot(IInventory inventory) {
		this(inventory, null, -1);
	}
	
	public NCSlot(IInventory inventory, BlockSide... blockSides) {
		this(inventory, null, -1, blockSides);
	}
	
	public NCSlot(IInventory inventory, ItemStack stack, int slotIndex, BlockSide... blockSides) {
		super(inventory, slotIndex, 0, 0);
		this.sides = BlockSide.toByte(blockSides);
		this.stack = stack;
	}
	
	public NCSlot(IInventory inventory, ItemStack stack, int slotIndex) {
		this(inventory, stack, slotIndex, BlockSide.values());
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
	public ItemStack getStack() {
		NCLogger.debug("stack is: %s", stack);
		return stack;
	}
	
	@Override
	public void putStack(ItemStack stack) {
		this.stack = stack;
		onSlotChanged();
	}
	
	@Override
	public ItemStack decrStackSize(int amount) {
		int toRemove = Math.min(stack.stackSize, amount);
		ItemStack decrStack = stack.copy();
		stack.stackSize -= toRemove;
		decrStack.stackSize = toRemove;
		if(stack.stackSize == 0){
			this.stack = null;
		}
		onSlotChanged();
		return decrStack;
	}
	
	public boolean isValidStackForSlot(ItemStack stack){
		return true;
	}
	
	public boolean isValidExtractionSide(int side){
		return BlockSide.isSideSelected(sides, side);
	}
	
	public boolean isValidInsertionSide(int side){
		return BlockSide.isSideSelected(sides, side);
	}
	
	public BlockSide[] getAccessibleSides(){
		return BlockSide.fromByte(sides);
	}

	@Override
	public void read(ByteArrayDataInput in) {
		
		//this byte contains bit flag wheter or not it contains nbt data and the sides it is able to be extracted and inserted for
		byte nbtAndSides = in.readByte();
		this.slotNumber = in.readInt();
		this.sides = (byte) (nbtAndSides & 0x3f);
		if(BitUtils.isOn(nbtAndSides, 8)){
			this.stack = new ItemStack(in.readInt(), in.readInt(), in.readInt());
			if(BitUtils.isOn(nbtAndSides, 7)){
				try {
					stack.stackTagCompound = (NBTTagCompound) NBTTagCompound.readNamedTag(in);
				} catch (IOException e) {
					//TODO some proper error handling
					NCLogger.warn("failed to read nbt data for ItemStack: %s:%s", e.getClass().getName(), e.getMessage());
				} catch(ClassCastException e){
					NCLogger.warn("nbt data from ItemStack didn't begin with a NBTTagCompound!");
				}
			}
		}
	}

	@Override
	public void write(ByteArrayDataOutput out) {
		if(stack != null){
			
			out.writeByte(sides | (stack.stackTagCompound != null ? 0xF0 : 0x80));
			out.writeInt(slotNumber);
			out.writeInt(stack.itemID);
			out.writeInt(stack.stackSize);
			out.writeInt(stack.getItemDamage());
			
			
			if(stack.stackTagCompound != null){
				try {
					NBTBase.writeNamedTag(stack.stackTagCompound, out);
				} catch (IOException e) {
					//TODO some proper error handling
					NCLogger.warn("failed to write nbt data for ItemStack: %s:%s", e.getClass().getName(), e.getMessage());
				}
			}
		} else{
			out.writeByte(sides);
			out.writeInt(slotNumber);
		}
	}
	
	@Override
	public String toString() {
		return String.format("NCSlot [itemID=%d, stackSize=%d, connSides: %s]", stack != null ? stack.itemID : -1, stack != null ? stack.stackSize : -1, Arrays.toString(BlockSide.fromByte(sides)));
	}
}
