package nl.besuikerd.networkcraft.generic;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import nl.besuikerd.networkcraft.core.BlockSide;
import nl.besuikerd.networkcraft.core.NCIconRegister;
import nl.besuikerd.networkcraft.core.NCLogger;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCable extends BlockConnecting{

	public static final float BASE_MAX_POS = .7f, BASE_MIN_POS = .3f, CONN_MAX_POS = .6f, CONN_MIN_POS = .4f;
	
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
		
	    setBlockBounds(BASE_MIN_POS, BASE_MIN_POS, BASE_MIN_POS, BASE_MAX_POS, BASE_MAX_POS, BASE_MAX_POS);
	    super.addCollisionBoxesToList(world, x, y, z, par5AxisAlignedBB, par6List, par7Entity);
	    
	    TileEntity tile = world.getBlockTileEntity(x, y, z);
	    if(tile instanceof TileEntityConnecting){
	    	boolean[] connections = ((TileEntityConnecting) tile).getConnectedSides();
	    	
	    	for(BlockSide side : BlockSide.values()){
	    		if(connections[side.ordinal()]){
		    		setBlockBounds(side);
		    		super.addCollisionBoxesToList(world, x, y, z, par5AxisAlignedBB, par6List, par7Entity);	 
	    		}
	    	}
	    }
	    
	    setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }
	
	private void setBlockBounds(BlockSide side){
		switch(side){
		case TOP:
    		setBlockBounds(CONN_MIN_POS, CONN_MIN_POS, CONN_MIN_POS, CONN_MAX_POS, 1, CONN_MAX_POS);
			break;
		case BOTTOM:
    		setBlockBounds(CONN_MIN_POS, 0, CONN_MIN_POS, CONN_MAX_POS, CONN_MAX_POS, CONN_MAX_POS);
    		break;
		case NORTH:
    		setBlockBounds(CONN_MIN_POS, CONN_MIN_POS, 0, CONN_MAX_POS, CONN_MAX_POS, CONN_MAX_POS);
    		break;
		case EAST:
    		setBlockBounds(CONN_MIN_POS, CONN_MIN_POS, CONN_MIN_POS, 1, CONN_MAX_POS, CONN_MAX_POS);
    	    break;
		case SOUTH:
    		setBlockBounds(CONN_MIN_POS, CONN_MIN_POS, CONN_MIN_POS, CONN_MAX_POS, CONN_MAX_POS, 1);
    	    break;
		case WEST:
    		setBlockBounds(0, CONN_MIN_POS, CONN_MIN_POS, CONN_MAX_POS, CONN_MAX_POS, CONN_MAX_POS);
    		break;
		}
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
	    		BB.minX = BB.minX + BASE_MIN_POS;
	    	if(!connectArray[BlockSide.BOTTOM.ordinal()])
	    		BB.minY = BB.minY + BASE_MIN_POS;
	    	if(!connectArray[BlockSide.NORTH.ordinal()])
	    		BB.minZ = BB.minZ + BASE_MIN_POS;
	    	if(!connectArray[BlockSide.EAST.ordinal()])
	    		BB.maxX = BB.maxX - BASE_MIN_POS;
	    	if(!connectArray[BlockSide.TOP.ordinal()])
	    		BB.maxY = BB.maxY - BASE_MIN_POS;
	    	if(!connectArray[BlockSide.SOUTH.ordinal()])
	    		BB.maxZ = BB.maxZ - BASE_MIN_POS;
	    
			return BB.copy();
	    }else{
            return super.getSelectedBoundingBoxFromPool(world, x, y, z).expand(-0.85F, -0.85F, -0.85F);
	    }
    }
	
	@Override
	public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3 origin, Vec3 direction)
    {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		
		if(tile instanceof TileEntityConnecting){
			boolean[] connections = ((TileEntityConnecting) tile).getConnectedSides();
			double minLengthSquared = Double.POSITIVE_INFINITY;
	        MovingObjectPosition result = null;
	        
	        setBlockBounds(BASE_MIN_POS, BASE_MIN_POS, BASE_MIN_POS, BASE_MAX_POS, BASE_MAX_POS, BASE_MAX_POS);
            MovingObjectPosition hitBase = super.collisionRayTrace(world, x, y, z, origin, direction);
            if(hitBase!=null){
                double lengthSquared = hitBase.hitVec.squareDistanceTo(origin);
                if (lengthSquared < minLengthSquared) {
                    minLengthSquared = lengthSquared;
                    result = hitBase;
                }
            }
		
			for (BlockSide side : BlockSide.values()) {
	            if (connections[side.ordinal()]) {
                    setBlockBounds(side);
                    MovingObjectPosition hit = super.collisionRayTrace(world, x, y, z, origin, direction);
                    if(hit!=null){
	                    double lengthSquared = hit.hitVec.squareDistanceTo(origin);
	                    if (lengthSquared < minLengthSquared) {
	                        minLengthSquared = lengthSquared;
	                        result = hit;
	                    }
                    }
	            }
			}
			
			setBlockBounds(0, 0, 0, 1, 1, 1);
			
			return result;
    	}
		
		return super.collisionRayTrace(world, x, y, z, origin, direction);
    }
	
	public boolean canBeReplacedByLeaves(World world, int x, int y, int z)
    {
        return false;
    }
}
