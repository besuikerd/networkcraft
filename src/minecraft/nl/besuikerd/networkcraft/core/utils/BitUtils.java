package nl.besuikerd.networkcraft.core.utils;

public class BitUtils {
	public static byte getByte(int num, int n){
		return (byte) (num >> (8 * (4 - n - 1)) & 255);
	}
	
	/**
	 * checks if the nth bit is on
	 * @param b
	 * @param n
	 * @return
	 */
	public static boolean isOn(byte b, int n){
		return ((b >> (n - 1)) & 1) == 1;
	}
}
