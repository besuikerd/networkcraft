package com.besuikerd.networkcraft.dv;

import java.util.Map;

import com.besuikerd.core.tileentity.TileEntityBesu;

public class TileEntityDestination extends TileEntityNode implements IDestination{

	protected Destination destination;
	
	public TileEntityDestination() {
		this.destination = new Destination(this, 1); 
		this.node = destination;
	}
	
	@Override
	public Map<IDestination, Integer> vectors() {
		return destination.vectors();
	}


	@Override
	public void costChange(Map<IDestination, Integer> destinations) {
		destination.costChange(destinations);
	}
}
