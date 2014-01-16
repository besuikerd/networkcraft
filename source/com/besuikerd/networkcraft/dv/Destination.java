package com.besuikerd.networkcraft.dv;

import java.util.Map;

import net.minecraft.tileentity.TileEntity;

public class Destination extends Node implements IDestination{

	public Destination(TileEntity tile, int cost) {
		super(tile, cost);
	}

	@Override
	public Map<IDestination, Integer> vectors() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void costChange(Map<IDestination, Integer> destinations) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String toString() {
		return String.format("Destination(%d,%d,%d)", x(), y(), z());
	}
}
