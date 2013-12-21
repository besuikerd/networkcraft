package nl.besuikerd.networkcraft.graph;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import nl.besuikerd.core.BlockSide;
import nl.besuikerd.networkcraft.block.BlockConnecting;

public abstract class BlockNetworkNode extends BlockConnecting{

	public BlockNetworkNode(int id, Material material) {
		super(id, material);
	}
}
