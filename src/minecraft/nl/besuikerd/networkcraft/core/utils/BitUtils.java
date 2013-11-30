package nl.besuikerd.networkcraft.core.utils;

public class BitUtils {
	public static byte getByte(int num, int n){
		return (byte) (num >> (8 * (4 - n - 1)) & 255);
	}
}
