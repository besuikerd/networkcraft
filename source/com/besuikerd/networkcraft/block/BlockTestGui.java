package com.besuikerd.networkcraft.block;

import com.besuikerd.core.BLogger;
import com.besuikerd.core.gui.GuiId;
import com.besuikerd.networkcraft.NetworkCraft;
import com.besuikerd.networkcraft.tileentity.TileEntityTestGui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockTestGui extends BlockDevice{

	public BlockTestGui(int id) {
		super(id);
		appendUnlocalizedName("gui");
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityTestGui();
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int unknown, float aX, float aY, float aZ) {
		player.openGui(NetworkCraft.instance, GuiId.GUITEST.getNumber(), world, x, y, z);
		return true;
	}

}
