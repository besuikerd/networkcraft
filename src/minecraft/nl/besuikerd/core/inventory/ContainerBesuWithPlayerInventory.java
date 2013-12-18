package nl.besuikerd.core.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;

public class ContainerBesuWithPlayerInventory extends ContainerBesu{
	@Override
	public void bindEntity(TileEntityInventory inventory, EntityPlayer player) {
		
		//attach player inventory
		for(int i = 0 ; i < 36 ; i++){
			Slot slot = new Slot(player.inventory, i, 0, 0);
			addSlotToContainer(slot);
		}
		super.bindEntity(inventory, player);
	}
}
