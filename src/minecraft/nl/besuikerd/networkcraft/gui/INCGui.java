package nl.besuikerd.networkcraft.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public interface INCGui {
	public Object onOpenend(TileEntity entity, EntityPlayer player, World w, int x, int y, int z);
}
