package nl.besuikerd.networkcraft.graph;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class TileEntityEndPoint extends TileEntityNetworkNode implements IEndPoint{
	
	protected EndPoint endpoint;
	
	public TileEntityEndPoint() {
		this.endpoint = new EndPoint(this, 1);
		node = endpoint;
	}
	
}
