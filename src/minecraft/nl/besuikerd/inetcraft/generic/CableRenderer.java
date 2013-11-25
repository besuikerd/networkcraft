package nl.besuikerd.inetcraft.generic;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class CableRenderer extends TileEntitySpecialRenderer{
	
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d0, double d1,
			double d2, float f) {
		GL11.glPushMatrix();
        //This will move our renderer so that it will be on proper place in the world
        GL11.glTranslated(d0, d1, d2);
        bindTexture(new ResourceLocation("generic", "textures/blocks/cable.png"));
        
        GL11.glPushMatrix();
        /*Note that true tile entity coordinates (tileEntity.xCoord, etc) do not match to render coordinates (d, etc) that are calculated as [true coordinates] - [player coordinates (camera coordinates)]*/
        //renderBlockRail(tileentity, tileentity.worldObj, tileentity.xCoord, tileentity.yCoord, tileentity.zCoord, Generic.cable);
        CableRenderModel.instance.render(null, 0.0F, 0.0F, 0, 0.0F, 0.0F, 0.0625F);
        GL11.glPopMatrix();
        GL11.glPopMatrix();
       
		
		
	}

}
