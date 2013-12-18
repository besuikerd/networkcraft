package nl.besuikerd.core.utils;

public class BitUtils {
	
	/**
	 * retrieves the n-th byte of the given integer
	 */
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
	
	/**
	 * converts the 8 lsb of the given integer to a String
	 * @param b
	 * @return the 8 lsb of the given integer as a String
	 */
	public static String ByteToString(int b){
		return Integer.toBinaryString((b & 0xff) | 0x100).substring(1);
	}
}
