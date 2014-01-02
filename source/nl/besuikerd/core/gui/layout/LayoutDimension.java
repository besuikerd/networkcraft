package nl.besuikerd.core.gui.layout;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Android style dimension calculation
 * 
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
	WRAP_CONTENT

	;

}
