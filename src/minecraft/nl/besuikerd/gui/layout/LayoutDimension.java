package nl.besuikerd.gui.layout;

/**
 * Android style dimension calculation
 * @author Besuikerd
 *
 */
public enum LayoutDimension {
	/**
	 * dimension is not calculated at all; it is absolute
	 */
	ABSOLUTE,
	
	/**
	 * dimension will be as small as possible
	 */
	WRAP_CONTENT,
	
	/**
	 * dimension will be as big as the parent
	 */
	MATCH_PARENT
	;
}
