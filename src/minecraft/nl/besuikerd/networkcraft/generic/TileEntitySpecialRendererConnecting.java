package nl.besuikerd.networkcraft.generic;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import nl.besuikerd.networkcraft.core.BlockSide;

import org.lwjgl.opengl.GL11;

public abstract class TileEntitySpecialRendererConnecting extends TileEntitySpecialRenderer{

	@Override
	public void renderTileEntityAt(TileEntity entity, double x, double y,
			double z, float f) {
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		
		if(entity instanceof TileEntityConnecting){
			TileEntityConnecting entityConnecting = (TileEntityConnecting) entity;
			boolean[] connectingSides = entityConnecting.getConnectedSides();
			for(int i = 0 ; i < connectingSides.length ; i++){
				if(connectingSides[i]){
					renderConnection(entityConnecting, i, x, y, z, f);
				}
			}
		}
		
		renderBase(entity, x, y, z, f);
		GL11.glPopMatrix();
	}

	
	public void translateForSide(SimpleModelBase b, int side){
		switch(BlockSide.lookup(side)){
		case TOP:
			break;
		case BOTTOM:
			
			GL11.glRotated(Math.PI, 1d, 0, 0);
			//GL11.glTranslated(16 - m., y, z);
			break;
		case NORTH:
			GL11.glRotated(-0.5 * Math.PI, 0, 0, 1d);
			break;
		case EAST:
			GL11.glRotated(0.5 * Math.PI, 1d, 0, 0);
			break;
		case SOUTH:
			GL11.glRotated(0.5 * Math.PI, 0, 0, 1d);
			break;
		case WEST:
			GL11.glRotated(-0.5 * Math.PI, 1d, 0, 0);
		}
	}
	
	public abstract void renderBase(TileEntity entity, double x, double y, double z, float f);
	
	public abstract void renderConnection(TileEntityConnecting entity, int side, double x, double y, double z, float f);
}
