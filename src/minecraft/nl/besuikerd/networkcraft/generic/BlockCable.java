package nl.besuikerd.networkcraft.generic;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import nl.besuikerd.networkcraft.core.BlockSide;
import nl.besuikerd.networkcraft.core.NCIconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCable extends BlockConnecting{

	public static final float BASE_MAX_POS = .7f, BASE_MIN_POS = .3f, CONN_MAX_POS = .6f, CONN_MIN_POS = .4f, CONN_LENGTH = .3f;
	
	public BlockCable(int id) {
		super(id, Material.rock);
		setHardness(2f);
		setUnlocalizedName("cable");
		setCreativeTab(CreativeTabs.tabMisc);
		setStepSound(Block.soundMetalFootstep);
	}
	
	@Override
	public boolean connectsTo(TileEntity other) {
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityBlockCable();
	}
	
	@Override
	public void registerIcons(NCIconRegister reg) {
		this.blockIcon = reg.registerIcon("cable");
	}

	@Override
	public void renderConnection(World world, TileEntity other, int x, int y,
			int z, BlockSide side) {
		
	}
	
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity)
    {
		
	    this.setBlockBounds(BASE_MIN_POS, BASE_MIN_POS, BASE_MIN_POS, BASE_MAX_POS, BASE_MAX_POS, BASE_MAX_POS);
	    super.addCollisionBoxesToList(world, x, y, z, par5AxisAlignedBB, par6List, par7Entity);
	    
	    TileEntity tile = world.getBlockTileEntity(x, y, z);
	    if(tile instanceof TileEntityConnecting){
	    	boolean[] connections = ((TileEntityConnecting) tile).getConnectedSides();
	    	if(connections[BlockSide.TOP.ordinal()]){
	    		this.setBlockBounds(CONN_MIN_POS, CONN_MIN_POS, CONN_MIN_POS, CONN_MAX_POS, 1, CONN_MAX_POS);
	    	    super.addCollisionBoxesToList(world, x, y, z, par5AxisAlignedBB, par6List, par7Entity);
	    	}
	    	if(connections[BlockSide.BOTTOM.ordinal()]){
	    		this.setBlockBounds(CONN_MIN_POS, 0, CONN_MIN_POS, CONN_MAX_POS, CONN_MAX_POS, CONN_MAX_POS);
	    	    super.addCollisionBoxesToList(world, x, y, z, par5AxisAlignedBB, par6List, par7Entity);
	    	}
	    	if(connections[BlockSide.NORTH.ordinal()]){
	    		this.setBlockBounds(CONN_MIN_POS, CONN_MIN_POS, 0, CONN_MAX_POS, CONN_MAX_POS, CONN_MAX_POS);
	    	    super.addCollisionBoxesToList(world, x, y, z, par5AxisAlignedBB, par6List, par7Entity);
	    	}
	    	if(connections[BlockSide.EAST.ordinal()]){
	    		this.setBlockBounds(CONN_MIN_POS, CONN_MIN_POS, CONN_MIN_POS, 1, CONN_MAX_POS, CONN_MAX_POS);
	    	    super.addCollisionBoxesToList(world, x, y, z, par5AxisAlignedBB, par6List, par7Entity);
	    	}
	    	if(connections[BlockSide.SOUTH.ordinal()]){
	    		this.setBlockBounds(CONN_MIN_POS, CONN_MIN_POS, CONN_MIN_POS, CONN_MAX_POS, CONN_MAX_POS, 1);
	    	    super.addCollisionBoxesToList(world, x, y, z, par5AxisAlignedBB, par6List, par7Entity);
	    	}
	    	if(connections[BlockSide.WEST.ordinal()]){
	    		this.setBlockBounds(0, CONN_MIN_POS, CONN_MIN_POS, CONN_MAX_POS, CONN_MAX_POS, CONN_MAX_POS);
	    	    super.addCollisionBoxesToList(world, x, y, z, par5AxisAlignedBB, par6List, par7Entity);
	    	}
	    }
	    setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }
	
	@SideOnly(Side.CLIENT)
    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
	    if(tile instanceof TileEntityConnecting){
	    	boolean[] connectArray = ((TileEntityConnecting) tile).getConnectedSides();
	    	//NCLogger.debug("test");
	    	AxisAlignedBB BB = super.getSelectedBoundingBoxFromPool(world, x, y, z);
	    	if(!connectArray[BlockSide.WEST.ordinal()])
	    		BB.minX = BB.minX + CONN_LENGTH;
	    	if(!connectArray[BlockSide.BOTTOM.ordinal()])
	    		BB.minY = BB.minY + CONN_LENGTH;
	    	if(!connectArray[BlockSide.NORTH.ordinal()])
	    		BB.minZ = BB.minZ + CONN_LENGTH;
	    	if(!connectArray[BlockSide.EAST.ordinal()])
	    		BB.maxX = BB.maxX - CONN_LENGTH;
	    	if(!connectArray[BlockSide.TOP.ordinal()])
	    		BB.maxY = BB.maxY - CONN_LENGTH;
	    	if(!connectArray[BlockSide.SOUTH.ordinal()])
	    		BB.maxZ = BB.maxZ - CONN_LENGTH;
	    
			return BB.copy();
	    }else{
            return super.getSelectedBoundingBoxFromPool(world, x, y, z).expand(-0.85F, -0.85F, -0.85F);
	    }
    }
}
