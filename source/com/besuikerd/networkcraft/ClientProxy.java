package com.besuikerd.networkcraft;

import com.besuikerd.networkcraft.render.ScreenRenderer;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy{
	public static final String CLS = "com.besuikerd.networkcraft.ClientProxy";
	
	private static Renderers renderers;
	
	@Override
	public void registerRenderers() {
		renderers = new Renderers();
	}
	
	public static Renderers renderers(){
		return renderers;
	}
	
	public class Renderers{
		public final ISimpleBlockRenderingHandler screen = new ScreenRenderer();
	}
}
