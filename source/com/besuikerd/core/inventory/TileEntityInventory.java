package com.besuikerd.core.inventory;

import com.besuikerd.core.tileentity.TileEntityBesu;
import com.besuikerd.core.utils.NBTUtils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityInventory extends TileEntityBesu implements ISidedInventory{
	
	protected Inventory inventory;
	
	public TileEntityInventory() {
		inventory = new Inventory();
		initInventory();
	}
	
	protected void initInventory(){
		
	}
	
	@Override
	public int getSizeInventory() {
		return inventory.getSizeInventory();
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return inventory.getStackInSlot(i);
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		return inventory.decrStackSize(i, j);
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		return inventory.getStackInSlotOnClosing(i);
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemStack) {
		inventory.setInventorySlotContents(i, itemStack);
	}

	@Override
	public String getInvName() {
		return inventory.getInvName();
	}

	@Override
	public boolean isInvNameLocalized() {
		return inventory.isInvNameLocalized();
	}

	@Override
	public int getInventoryStackLimit() {
		return inventory.getInventoryStackLimit();
	}

	@Override
	public void onInventoryChanged() {
		inventory.onInventoryChanged();
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityPlayer) {
		return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : entityPlayer.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public void openChest() {
		inventory.openChest();
	}

	@Override
	public void closeChest() {
		inventory.closeChest();
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		return inventory.isItemValidForSlot(i, itemStack);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return inventory.getAccessibleSlotsFromSide(side);
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int side) {
		return inventory.canInsertItem(i, itemStack, side);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int side) {
		return inventory.canExtractItem(i, itemStack, side);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		NBTUtils.readProcessData(inventory, tag, "items");
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		NBTUtils.writeProcessData(inventory, tag, "items");
	}
	
	public Inventory getInventory() {
		return inventory;
	}

}
