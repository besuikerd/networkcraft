package nl.besuikerd.core.gui.texture;

import nl.besuikerd.core.utils.Tuple;

public enum Texture implements ITexture{
	ARROW_UP(new Tuple(96, 15, 102, 18)),
	ARROW_DOWN(new Tuple(103, 15, 109, 18))
	;
	
	private Tuple texture;

	private Texture(Tuple texture) {
		this.texture = texture;
	}
	
	public Tuple getTexture() {
		return texture;
	}
}
