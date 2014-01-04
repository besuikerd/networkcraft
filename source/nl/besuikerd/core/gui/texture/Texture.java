package nl.besuikerd.core.gui.texture;

import nl.besuikerd.core.utils.Tuple;

public enum Texture implements ITexture{
	ARROW_UP(new Tuple(96, 15, 102, 18)),
	ARROW_DOWN(new Tuple(103, 15, 109, 18)),
	ARROW_RIGHT(new Tuple(110, 15, 113, 21)),
	ARROW_LEFT(new Tuple(114, 15, 117, 21)),
	
	CHECK_MARK(new Tuple(168, 12, 175, 19)),
	
	RADIO_OUTER(new Tuple(214, 0, 229, 15)),
	RADIO_INNER(new Tuple(230, 0, 237, 7))
	;
	
	private Tuple texture;

	private Texture(Tuple texture) {
		this.texture = texture;
	}
	
	public Tuple getTexture() {
		return texture;
	}
}
