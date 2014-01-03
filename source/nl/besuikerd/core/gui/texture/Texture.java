package nl.besuikerd.core.gui.texture;

import nl.besuikerd.core.utils.Tuple;

public enum Texture implements ITexture{
	ARROW_UP(new Tuple(96, 15, 102, 18)),
	ARROW_DOWN(new Tuple(103, 15, 109, 18)),
	ARROW_RIGHT(new Tuple(110, 15, 113, 21)),
	ARROW_LEFT(new Tuple(114, 15, 117, 21))
	;
	
	private Tuple texture;

	private Texture(Tuple texture) {
		this.texture = texture;
	}
	
	public Tuple getTexture() {
		return texture;
	}
}
