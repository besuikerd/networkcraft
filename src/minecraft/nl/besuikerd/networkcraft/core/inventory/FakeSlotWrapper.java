package nl.besuikerd.networkcraft.core.inventory;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import nl.besuikerd.networkcraft.core.NCLogger;
import nl.besuikerd.networkcraft.core.utils.ReflectUtils;

public class FakeSlotWrapper extends Slot implements IFakeSlot{
	private Slot slot;
	
	public FakeSlotWrapper(Slot slot) {
		super(slot.inventory, slot.getSlotIndex(), slot.xDisplayPosition, slot.yDisplayPosition);
		this.slot = slot;
	}
	
	@Override
	public boolean canTakeStack(EntityPlayer par1EntityPlayer) {
		return slot.canTakeStack(par1EntityPlayer);
	}
	
	@Override
	public ItemStack decrStackSize(int par1) {
		return slot.decrStackSize(par1);
	}
	
	@Override
	public Icon getBackgroundIconIndex() {
		return slot.getBackgroundIconIndex();
	}
	
	@Override
	public ResourceLocation getBackgroundIconTexture() {
		return slot.getBackgroundIconTexture();
	}
	
	@Override
	public boolean getHasStack() {
		return slot.getHasStack();
	}
	
	@Override
	public int getSlotIndex() {
		return slot.getSlotIndex();
	}
	
	@Override
	public int getSlotStackLimit() {
		return slot.getSlotStackLimit();
	}
	
	@Override
	public ItemStack getStack() {
		return slot.getStack();
	}
	
	@Override
	public boolean isItemValid(ItemStack par1ItemStack) {
		return slot.isItemValid(par1ItemStack);
	}
	
	@Override
	public boolean isSlotInInventory(IInventory par1iInventory, int par2) {
		return slot.isSlotInInventory(par1iInventory, par2);
	}
	
	@Override
	protected void onCrafting(ItemStack par1ItemStack) {
		ReflectUtils.invoke(slot, "onCrafting", par1ItemStack);
	}
	
	@Override
	protected void onCrafting(ItemStack par1ItemStack, int par2) {
		ReflectUtils.invoke(slot, "onCrafting", par1ItemStack, par2);
	}
	
	@Override
	public void onPickupFromSlot(EntityPlayer par1EntityPlayer,
			ItemStack par2ItemStack) {
		slot.onPickupFromSlot(par1EntityPlayer, par2ItemStack);
	}
	
	@Override
	public void onSlotChange(ItemStack par1ItemStack, ItemStack par2ItemStack) {
		slot.onSlotChange(par1ItemStack, par2ItemStack);
	}
	
	@Override
	public void onSlotChanged() {
		slot.onSlotChanged();
	}
	
	@Override
	public void putStack(ItemStack par1ItemStack) {
		slot.putStack(par1ItemStack);
	}
	
	@Override
	public void setBackgroundIcon(Icon icon) {
		slot.setBackgroundIcon(icon);
	}
	
	@Override
	public void setBackgroundIconTexture(ResourceLocation texture) {
		slot.setBackgroundIconTexture(texture);
	}
	
	@Override
	public boolean func_111238_b() {
		return slot.func_111238_b();
	}
	
	
}
