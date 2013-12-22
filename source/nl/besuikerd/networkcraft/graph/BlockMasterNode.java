package nl.besuikerd.networkcraft.graph;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import nl.besuikerd.core.BlockSide;
import nl.besuikerd.core.block.BlockConnecting;
import nl.besuikerd.core.block.BlockContainerBesu;
import nl.besuikerd.networkcraft.block.BlockDevice;

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
