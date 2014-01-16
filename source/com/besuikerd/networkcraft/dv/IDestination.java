package com.besuikerd.networkcraft.dv;

import java.util.Map;

public interface IDestination extends INode{
	public Map<IDestination, Integer> vectors();
	public void costChange(Map<IDestination, Integer> destinations);
}
