package nl.besuikerd.networkcraft;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;

public class NCIconRegister implements IconRegister{

	private IconRegister parent;
	
	
	public NCIconRegister(IconRegister parent) {
		this.parent = parent;
	}

	@Override
	public Icon registerIcon(String s) {
		return parent.registerIcon("networkcraft:".concat(s));
	}
	
}