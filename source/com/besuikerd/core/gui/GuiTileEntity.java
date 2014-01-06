package com.besuikerd.core.gui;

import com.besuikerd.core.inventory.ContainerBesu;
import com.besuikerd.core.inventory.TileEntityInventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuiTileEntity extends GuiBase{

	protected TileEntityInventory tile;
	protected EntityPlayer player;
	protected World world;
	
	public GuiTileEntity(ContainerBesu container) {
		super(container);
	}

	protected void bindTileEntity(TileEntityInventory entity, EntityPlayer player, World world){
		this.tile = entity;
		this.player = player;
		this.world = world;
	}
}
