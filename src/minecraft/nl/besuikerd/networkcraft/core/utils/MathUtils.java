package nl.besuikerd.networkcraft.core.utils;

public class MathUtils {
	
	public static <E extends Comparable<E>> boolean inRange(E element, E min, E max){
		int toMin = element.compareTo(min);
		int toMax = element.compareTo(max);
		return toMin == 0 ? true : toMin < 0 ? false : toMax == 0 ? true : toMax > 0 ? false : true;
	}
	
	
	public static <E extends Comparable<E>> boolean inRange2D(E el1, E el2, E min, E max){
		return inRange(el1, min, max) && inRange(el2, min, max);
	}
	
	public static <E extends Comparable<E>> boolean inRange2D(E el1, E el2, E min1, E max1, E min2, E max2){
		return inRange(el1, min1, max1) && inRange(el2, min2, max2);
	}
}
