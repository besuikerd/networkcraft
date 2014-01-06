package com.besuikerd.core.inventory;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;

import com.besuikerd.core.BlockSide;
import com.besuikerd.core.inventory.InventoryStackBesu.StackBuilder;
import com.besuikerd.core.packet.IProcessData;
import com.besuikerd.core.utils.IntList;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

public class InventoryBesu implements ISidedInventory, IProcessData{
	
	protected List<InventoryStackBesu> stacks;
	
	protected IntList[] accessibleSides;
	
	protected String name;
	
	public InventoryBesu() {
		this.name = "besu.inv";
		this.stacks = new ArrayList<InventoryStackBesu>();
		this.accessibleSides = new IntList[6];
		for(int i = 0 ; i < accessibleSides.length ; i++){
			accessibleSides[i] = IntList.rawIntList();
		}
	}
	
	public void add(InventoryStackBesu stack){
		stacks.add(stack);
		for(BlockSide b : BlockSide.fromByte(stack.sides)){
			accessibleSides[b.ordinal()].add(stacks.size() - 1);
		}
	}
	
	public void addStacks(int n, StackBuilder builder){
		for(int i = 0 ; i < n ; i++){
			add(builder.build());
		}
	}
	
	@Override
	public int getSizeInventory() {
		return stacks.size();
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return stacks.get(i).stack;
	}

	@Override
	public ItemStack decrStackSize(int i, int amount) {
		InventoryStackBesu invStack = stacks.get(i);
		ItemStack stack = invStack.stack;
		int toRemove = Math.min(stack.stackSize, amount);
		ItemStack decrStack = stack.copy();
		stack.stackSize -= toRemove;
		decrStack.stackSize = toRemove;
		if(stack.stackSize == 0){
			invStack.stack = null;
		}
		return decrStack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		if(stacks.size() > i){
			ItemStack stack = stacks.get(i).stack;
			if(stack != null){
				stacks.set(i, null);
				return stack;
			}
		}
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		InventoryStackBesu invStack = stacks.get(i);
		if(invStack != null){
			invStack.stack = itemstack;
		}
	}

	@Override
	public String getInvName() {
		return name;
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void onInventoryChanged() {
		
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return true;
	}

	@Override
	public void openChest() {
	}

	@Override
	public void closeChest() {
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		if(i < stacks.size()){
			InventoryStackBesu invStack = stacks.get(i);
			return invStack.stack == null || invStack.isReal && itemstack.isItemEqual(invStack.stack);
		}
		return false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return accessibleSides[side].rawIntArray();
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int side) {
		if(i < stacks.size()){
			InventoryStackBesu invStack = stacks.get(i);
			return invStack.stack == null || invStack.isReal && itemStack.isItemEqual(invStack.stack) && BlockSide.isSideSelected(invStack.sides, side);
		}
		return false;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int side) {
		if(i < stacks.size()){
			InventoryStackBesu invStack = stacks.get(i);
			return invStack.stack == null || invStack.isReal && itemStack.isItemEqual(invStack.stack) && BlockSide.isSideSelected(invStack.sides, side);
		}
		return false;
	}

	@Override
	public void read(ByteArrayDataInput in) {
		stacks.clear();
		int length = in.readInt();
		for(int i = 0 ; i < length ; i++){
			InventoryStackBesu inv = new InventoryStackBesu();
			inv.read(in);
			stacks.add(inv);
		}
	}

	@Override
	public void write(ByteArrayDataOutput out) {
		out.writeInt(stacks.size());
		for(InventoryStackBesu inv : stacks){
			inv.write(out);
		}
	}

}
