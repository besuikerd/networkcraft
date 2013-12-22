package nl.besuikerd.networkcraft.graph;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import nl.besuikerd.core.BlockSide;
import nl.besuikerd.networkcraft.block.BlockConnecting;
import nl.besuikerd.networkcraft.block.BlockDevice;
import nl.besuikerd.networkcraft.block.BlockNetworkContainer;

public class BlockMasterNode extends BlockDevice{

	public BlockMasterNode(int id) {
		super(id);
		appendUnlocalizedName("master");
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityMasterNode();
	}
}
