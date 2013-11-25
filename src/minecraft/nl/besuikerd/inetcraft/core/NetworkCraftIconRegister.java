package nl.besuikerd.inetcraft.core;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;

public class NetworkCraftIconRegister implements IconRegister{

	private IconRegister parent;
	
	public NetworkCraftIconRegister(IconRegister parent) {
		this.parent = parent;
	}

	@Override
	public Icon registerIcon(String s) {
		return parent.registerIcon("networkcraft:".concat(s));
	}
	
}