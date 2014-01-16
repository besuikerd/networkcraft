package com.besuikerd.networkcraft.dv;

public interface INode extends Comparable<INode> {
	public int cost();
	public int x();
	public int y();
	public int z();
}
