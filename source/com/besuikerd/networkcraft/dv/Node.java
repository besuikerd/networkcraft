package com.besuikerd.networkcraft.dv;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.besuikerd.core.BlockSide;
import com.besuikerd.core.ServerLogger;
import com.besuikerd.core.utils.collection.SafeConstrainedMap;
import com.besuikerd.core.utils.collection.SmallerThanMapConstraint;
import com.google.common.base.Supplier;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

public class Node implements INode{
	
	protected TileEntity tile;
	protected int cost;
	
	public Node(TileEntity tile, int cost) {
		this.tile = tile;
		this.cost = cost;
	}

	@Override
	public int cost() {
		return cost;
	}
	
	public void update(World world, int x, int y, int z){
		Set<INode> processedNodes = new HashSet<INode>();
		Multimap<Integer, INode> foundNodes =  Multimaps.newSetMultimap(new TreeMap<Integer, Collection<INode>>(), new Supplier<Set<INode>>(){
			@Override
			public Set<INode> get() {
				return new HashSet<INode>();
			}
		}); //multimap with ordered keys, unordered values
		Map<IDestination, Integer> destinations = new HashMap<IDestination, Integer>();
		destinations = SafeConstrainedMap.create(destinations, new SmallerThanMapConstraint<IDestination, Integer>(destinations));
		
		int count = 0;
		for(INode n : BlockSide.blockSideIterator(INode.class, tile.worldObj, x(), y(), z())){
			count++;
		}
		
		ServerLogger.debug("count: %d", count);
		
		if(count > 1){
			prim(this, 0, destinations, processedNodes, foundNodes);
			ServerLogger.debug("destinations: %s", destinations);
		}
		
		for(Map.Entry<IDestination, Integer> entry : destinations.entrySet()){
			entry.getKey().costChange(destinations);
		}
		
		
	}
	
	/**
	 * prim's algorithm to determine the minimal spanning tree for 
	 * @param current
	 * @param cost
	 * @param destinations
	 * @param processedNodes
	 * @param foundNodes
	 */
	private void prim(INode current, int cost, Map<IDestination, Integer> destinations, Set<INode> processedNodes, Multimap<Integer, INode> foundNodes){
		processedNodes.add(current);
		for(INode n : BlockSide.blockSideIterator(INode.class, tile.worldObj, current.x(), current.y(), current.z())){
			if(!processedNodes.contains(n)){
				int c = cost + n.cost();
				foundNodes.put(c, n);
			}
		}
		//grab the cheapest nodes
		Iterator<Integer> iterator = foundNodes.keySet().iterator();
		if(foundNodes.size() > 0){
			int c = iterator.next();
			Collection<INode> cheapestNodes = foundNodes.removeAll(c);
			for(INode n : cheapestNodes){
				if(n instanceof IDestination){
					destinations.put((IDestination) n, cost + n.cost());
				}
				prim(n, cost + n.cost(), destinations, processedNodes, foundNodes);
			}
		}
		
	}

	@Override
	public int x() {
		return tile.xCoord;
	}

	@Override
	public int y() {
		return tile.yCoord;
	}

	@Override
	public int z() {
		return tile.zCoord;
	}
	
	@Override
	public int compareTo(INode n) {
		return cost() - n.cost() ;//> 0 ? 1 : -1;
	}
	
	@Override
	public int hashCode() {
		return x() + y() + z();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof INode){
			INode n = (INode) obj;
			return n.x() == x() && n.y() == y() && n.z() == z();
		}
		return false;
	}
	
	@Override
	public String toString() {
		return String.format("Node(%d,%d,%d)", x(), y(), z());
	}
}
