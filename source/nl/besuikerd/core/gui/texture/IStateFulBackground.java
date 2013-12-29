package nl.besuikerd.core.gui.texture;

/**
 * texture that changes depending on state
 * @author Besuikerd
 *
 */
public interface IStateFulBackground<E> {
	public ITexturedBackground backgroundForState(E state);
}
