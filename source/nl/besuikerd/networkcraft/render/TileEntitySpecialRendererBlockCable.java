package nl.besuikerd.networkcraft.render;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import nl.besuikerd.core.BLogger;
import nl.besuikerd.core.BlockSide;
import nl.besuikerd.render.SimpleModelBase;
import nl.besuikerd.render.TileEntitySpecialRendererConnecting;
import nl.besuikerd.render.SimpleModelBase.ShapeBuilder;

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
			.offset(-1, 3, -1),
			
			new SimpleModelBase.ShapeBuilder()
			.dimension(4, 1, 4)
			.offset(-2, 6, -2),
			
			new SimpleModelBase.ShapeBuilder()
			.dimension(6, 1, 6)
			.offset(-3, 5, -3)
	);
	
	@Override
	public void renderBase(TileEntity entity, double x, double y, double z,
			float f) {
		bindTexture(new ResourceLocation("networkcraft", "textures/blocks/cable.png"));
		rendererBase.render(null, 0f, 0f, 0f, 0f, 0f, 0.0625f);
	}

	private static boolean isLogged = false;
	
	@Override
	public void renderConnection(TileEntity entity, BlockSide side,
			double x, double y, double z, float f) {		
		
		GL11.glPushMatrix();
		translateForSide(rendererConnection, side);
		rendererConnection.render(null, 0f, 0f, 0f, 0f, 0f, 0.0625f);
		GL11.glPopMatrix();
	}
}
