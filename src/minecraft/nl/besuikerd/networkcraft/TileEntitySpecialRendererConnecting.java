package nl.besuikerd.networkcraft;

import java.util.Arrays;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import nl.besuikerd.core.BlockSide;
import nl.besuikerd.core.BLogger;

import org.lwjgl.opengl.GL11;

public abstract class TileEntitySpecialRendererConnecting extends TileEntitySpecialRenderer{

	@Override
	public void renderTileEntityAt(TileEntity entity, double x, double y,
			double z, float f) {
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		
		
		renderBase(entity, x, y, z, f);
		
		if(entity instanceof TileEntityConnecting){
			TileEntityConnecting entityConnecting = (TileEntityConnecting) entity;
			boolean[] connectingSides = entityConnecting.getConnectedSides();
			GL11.glPushMatrix();
			GL11.glTranslated(0.5d, 0.5d, 0.5d);
			for(int i = 0 ; i < connectingSides.length ; i++){
				if(connectingSides[i]){
					renderConnection(entityConnecting, i, x, y, z, f);
				}
			}
			GL11.glPopMatrix();
		}
		
		GL11.glPopMatrix();
	}

	
	public void translateForSide(SimpleModelBase b, int side){
		switch(BlockSide.lookup(side)){
		case TOP:
			break;
		case BOTTOM:
			GL11.glRotated(180, 1d, 0, 0);
			break;
		case NORTH:
			GL11.glRotated(-90, 1d, 0, 0);
			break;
		case EAST:
			GL11.glRotated(-90, 0, 0, 1d);
			break;
		case SOUTH:
			GL11.glRotated(90, 1d, 0, 0);
			break;
		case WEST:
			GL11.glRotated(90, 0, 0, 1d);
			break;
		}
	}
	
	public abstract void renderBase(TileEntity entity, double x, double y, double z, float f);
	
	public abstract void renderConnection(TileEntityConnecting entity, int side, double x, double y, double z, float f);
}
