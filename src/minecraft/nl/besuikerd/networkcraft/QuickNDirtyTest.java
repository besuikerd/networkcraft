package nl.besuikerd.networkcraft;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Arrays;

import static nl.besuikerd.core.utils.FunctionalUtils.*;

import java.util.EnumSet;

import com.google.common.base.Function;

import nl.besuikerd.core.utils.BitUtils;
import nl.besuikerd.core.utils.MathUtils;
import nl.besuikerd.core.utils.ReflectUtils;

public class QuickNDirtyTest {
	
	public static void main(String[] args) {
		functionalTest();
		bitUtilsTest();
	}
	
	private static void functionalTest(){
		System.out.println(Arrays.toString(map(String.class, new String[]{"this", "should", "be", "uppercased"}, functionApplyFunction("toUpperCase", String.class, String.class))));
		
		System.out.println(ReflectUtils.newInstance(String.class, "nieuwe string"));
		
		System.out.println(foldl(100, new Integer[]{1,2,3}, new ABAFunction<Integer, Integer>() {
			@Override
			public Integer apply(Integer a, Integer b) {
				return a - b;
			}
		}));
		
		System.out.println(foldr(100, new Integer[]{1,2,3}, new ABBFunction<Integer, Integer>() {
			@Override
			public Integer apply(Integer b, Integer a) {
				return b - a;
			}
		}));
		
		System.out.println(foldl(false, new Integer[]{1,2,3}, functionAny(new Function<Integer, Boolean>() {
			@Override
			public Boolean apply(Integer input) {
				return input > 1;
			}
		})));
	}
	
	private static void bitUtilsTest(){
		System.out.println(BitUtils.ByteToString(BitUtils.toggleOff(0xff, 5)));
	}
}
