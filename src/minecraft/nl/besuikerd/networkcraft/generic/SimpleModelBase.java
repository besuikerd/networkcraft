package nl.besuikerd.networkcraft.generic;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import nl.besuikerd.networkcraft.core.NCLogger;

public class SimpleModelBase extends ModelBase{
	private List<ModelRenderer> renderers;
	
	public SimpleModelBase() {
		super();
		textureHeight = 64;
		textureWidth = 64;
		this.renderers = new ArrayList<ModelRenderer>();
	}
	
	public ModelRenderer attach(){
		ModelRenderer renderer = new ModelRenderer(this);
		renderers.add(renderer);
		return renderer;
	}
	
	@Override
	public void render(Entity par1Entity, float par2, float par3, float par4,
			float par5, float par6, float par7) {
		super.render(par1Entity, par2, par3, par4, par5, par6, par7);
		for(ModelRenderer m : renderers){
			m.render(par5);
		}
	}
}
