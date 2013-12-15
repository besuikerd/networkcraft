package nl.besuikerd.networkcraft.core.inventory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import nl.besuikerd.networkcraft.core.BlockSide;
import nl.besuikerd.networkcraft.core.TileEntityNC;
import nl.besuikerd.networkcraft.core.packet.IProcessData;
import nl.besuikerd.networkcraft.core.utils.IntList;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class TileEntityInventory extends TileEntityNC implements ISidedInventory{
	
	protected List<NCSlot> slots;
	protected IntList[] accessibleSides;
	
	public TileEntityInventory() {
		slots = new ArrayList<NCSlot>();
		accessibleSides = new IntList[6];
		for(int i = 0 ; i < accessibleSides.length ; i++){
			accessibleSides[i] = IntList.rawIntList();
		}
	}
	
	
	public void addSlot(NCSlot slot){
		slots.add(slot);
		slot.slotNumber = slots.size() - 1;
		for(BlockSide b : slot.getAccessibleSides()){
			accessibleSides[b.ordinal()].add(slot.slotNumber);
		}
	}
	
	public boolean hasPlayerInventory(){
		return true;
	}
	
	public void addSlots(Collection<NCSlot> slots){
		for(NCSlot slot : slots){
			addSlot(slot);
		}
	}
	
	@Override
	public int getSizeInventory() {
		return slots.size();
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return slots.get(i).getStack();
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		return slots.get(i).decrStackSize(j);
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		if(slots.size() > i){
			ItemStack stack = slots.get(i).getStack();
			if(stack != null){
				slots.set(i, null);
				return stack;
			}
		}
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemStack) {
		NCSlot slot = slots.get(i);
		if(slot != null){
			slot.stack = itemStack;
		} else{
			slots.set(i, new NCSlot(this, itemStack, i));
		}
	}

	@Override
	public String getInvName() {
		//TODO create a system for this
		return "SidedInventory";
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
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityPlayer) {
		return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : entityPlayer.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public void openChest() {
		
	}

	@Override
	public void closeChest() {
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		if(i < slots.size()){
			NCSlot s = slots.get(i);
			return s == null ? false : s.isValidStackForSlot(itemStack);
		}
		return false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return accessibleSides[side].rawIntArray();
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int side) {
		if(i < slots.size()){
			NCSlot s = slots.get(i);
			return s == null ? false : s.isValidStackForSlot(itemStack) && s.isValidInsertionSide(side);
		}
		return false;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int side) {
		if(i < slots.size()){
			NCSlot s = slots.get(i);
			return s == null ? false : s.isValidStackForSlot(itemStack) && s.isValidExtractionSide(side);
		}
		return false;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		ByteArrayDataInput in = ByteStreams.newDataInput(tag.getByteArray("items"));
		slots.clear();
		int size = in.readInt();
		for(int i = 0 ; i < size ; i++){
			NCSlot slot = new NCSlot(this, null, slots.size() - 1);
			slot.read(in);
			slots.add(slot);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeInt(slots.size());
		for(IProcessData d : slots){
			d.write(out);
		}
		tag.setByteArray("items", out.toByteArray());
	}

}
