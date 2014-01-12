package com.besuikerd.core.inventory;

import java.io.IOException;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

import com.besuikerd.core.BLogger;
import com.besuikerd.core.BlockSide;
import com.besuikerd.core.packet.IProcessData;
import com.besuikerd.core.utils.BitUtils;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

public class InventoryStack implements IProcessData{
	protected ItemStack stack;
	protected int stackLimit;
	protected byte sides;
	protected boolean isReal;
	
	protected InventoryGroup group;
	
	public ItemStack getStack() {
		return stack;
	}
	
	public BlockSide[] getBlockSides() {
		return BlockSide.fromByte(sides);
	}
	
	public byte getSides(){
		return sides;
	}
	
	public int getStackLimit() {
		return stackLimit;
	}
	
	public boolean isReal() {
		return isReal;
	}
	
	public InventoryGroup getGroup() {
		return group;
	}
	
	@Override
	public void read(ByteArrayDataInput in) {
		boolean hasStack = in.readBoolean();
		boolean hasCompound = in.readBoolean();
		
		if(hasStack){
			this.stack = new ItemStack(in.readInt(), in.readInt(), in.readInt());
			if(hasCompound){
				try {
					stack.stackTagCompound = (NBTTagCompound) NBTTagCompound.readNamedTag(in);
				} catch (IOException e) {
					//TODO some proper error handling
					BLogger.warn("failed to read nbt data for ItemStack: %s:%s", e.getClass().getName(), e.getMessage());
				} catch(ClassCastException e){
					BLogger.warn("nbt data from ItemStack didn't begin with a NBTTagCompound!");
				}
			}
		}
	}
	
	@Override
	public void write(ByteArrayDataOutput out) {
		// TODO Auto-generated method stub
		out.writeBoolean(stack != null);
		out.writeBoolean(stack != null && stack.stackTagCompound != null);
		
		if(stack != null){
			out.writeInt(stack.itemID);
			out.writeInt(stack.stackSize);
			out.writeInt(stack.getItemDamage());
			if(stack.stackTagCompound != null){
				try {
					NBTBase.writeNamedTag(stack.stackTagCompound, out);
				} catch (IOException e) {
					//TODO some proper error handling
					BLogger.warn("failed to write nbt data for ItemStack: %s:%s", e.getClass().getName(), e.getMessage());
				}
			}
		}
	}
	
//	@Override
//	public void write(ByteArrayDataOutput out) {
//		//only 7 lsb of stackLimit will be stored (stackLimit should never exceed 64 anyways)
//		out.writeByte( (stackLimit & 0x7f) | (isReal ? 0x80 : 0) );
//		//write sides and wheter ItemStack exists and stackTagCompound exists
//		
////		BLogger.debug("writing: [%s] [%s]", BitUtils.ByteToString((stackLimit & 0x7f) | (isReal ? 0x80 : 0)), BitUtils.ByteToString(sides | (stack != null ? 0x80 : 0) | (stack != null && stack.stackTagCompound != null ? 0x40 : 0)));
//		out.writeByte(sides | (stack != null ? 0x80 : 0) | (stack != null && stack.stackTagCompound != null ? 0x40 : 0) );
//		
//		if(stack != null){
//			out.writeInt(stack.itemID);
//			out.writeInt(stack.stackSize);
//			out.writeInt(stack.getItemDamage());
//			if(stack.stackTagCompound != null){
//				try {
//					NBTBase.writeNamedTag(stack.stackTagCompound, out);
//				} catch (IOException e) {
//					//TODO some proper error handling
//					BLogger.warn("failed to write nbt data for ItemStack: %s:%s", e.getClass().getName(), e.getMessage());
//				}
//			}
//		}
//	}
//	
//	@Override
//	public void read(ByteArrayDataInput in) {
//		byte stackLimitAndIsReal = in.readByte();
//		this.isReal = BitUtils.isOn(stackLimitAndIsReal, 8);
//		this.stackLimit = stackLimitAndIsReal & 0x7f;
//		
//		byte sidesAndStack = in.readByte();
//		this.sides = (byte) (sidesAndStack & 0x3f);
//		
////		BLogger.debug("bit1: %s, bit2: %s", BitUtils.ByteToString(stackLimitAndIsReal), BitUtils.ByteToString(sidesAndStack));
//		
//		if(BitUtils.isOn(sidesAndStack, 8)){
//			this.stack = new ItemStack(in.readInt(), in.readInt(), in.readInt());
//			if(BitUtils.isOn(sidesAndStack, 7)){
//				try {
//					stack.stackTagCompound = (NBTTagCompound) NBTTagCompound.readNamedTag(in);
//				} catch (IOException e) {
//					//TODO some proper error handling
//					BLogger.warn("failed to read nbt data for ItemStack: %s:%s", e.getClass().getName(), e.getMessage());
//				} catch(ClassCastException e){
//					BLogger.warn("nbt data from ItemStack didn't begin with a NBTTagCompound!");
//				}
//			}
//		}
//	}
	
	public static class StackBuilder{
		protected boolean isReal;
		protected BlockSide[] blockSides;
		protected int stackLimit;
		
		public StackBuilder() {
			this.isReal = true;
			this.blockSides = BlockSide.values();
			this.stackLimit = 64;
		}
		
		public StackBuilder real(boolean isReal){
			this.isReal = isReal;
			return this;
		}
		
		public StackBuilder sides(BlockSide... sides){
			this.blockSides = sides;
			return this;
		}
		
		public StackBuilder stackLimit(int stackLimit){
			this.stackLimit = stackLimit;
			return this;
		}
		
		public InventoryStack build(){
			InventoryStack inv = new InventoryStack();
			inv.isReal = this.isReal;
			inv.sides = BlockSide.toByte(this.blockSides);
			inv.stackLimit = this.stackLimit;
			return inv;
		}
	}
}
