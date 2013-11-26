package nl.besuikerd.networkcraft.generic;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import nl.besuikerd.networkcraft.core.NCLogger;

import org.lwjgl.opengl.GL11;

public class TileEntitySpecialRendererBlockCable extends TileEntitySpecialRendererConnecting{

	private static final SimpleModelBase rendererBase = new SimpleModelBase(64, 64, 
		new SimpleModelBase.ShapeBuilder()
		.dimension(6, 6, 6)
		.offset(5, 5, 5)
	);
	private static final SimpleModelBase rendererConnection = new SimpleModelBase(64, 64, 
			new SimpleModelBase.ShapeBuilder()
			.dimension(2, 5, 2)
			.offset(-1, 3, -1)
	);
	
	@Override
	public void renderBase(TileEntity entity, double x, double y, double z,
			float f) {
		bindTexture(new ResourceLocation("networkcraft", "textures/blocks/cable.png"));
		rendererBase.render(null, 0f, 0f, 0f, 0f, 0f, 0.0625f);
	}

	private static boolean isLogged = false;
	
	@Override
	public void renderConnection(TileEntityConnecting entity, int side,
			double x, double y, double z, float f) {		
		
		GL11.glPushMatrix();
		translateForSide(rendererConnection, side);
		rendererConnection.render(null, 0f, 0f, 0f, 0f, 0f, 0.0625f);
		GL11.glPopMatrix();
	}
}
