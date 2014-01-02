package nl.besuikerd.core.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public interface IGuiBesu {
	public Object onOpenend(TileEntity entity, EntityPlayer player, World w, int x, int y, int z);
}
