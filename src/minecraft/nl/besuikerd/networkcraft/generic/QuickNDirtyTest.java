package nl.besuikerd.networkcraft.generic;

import java.util.Arrays;

import nl.besuikerd.networkcraft.core.utils.MathUtils;
import nl.besuikerd.networkcraft.core.utils.ReflectUtils;

public class QuickNDirtyTest {
	public static void main(String[] args) {
		System.out.println(Arrays.toString(ReflectUtils.map(ReflectUtils.functionApplyFunction("toUpperCase", String.class, String.class), String.class, new String[]{"this", "should", "be", "uppercased"})));
		System.out.println(ReflectUtils.newInstance(String.class, "nieuwe string"));
		System.out.println(200 + 50 >> 1);
	}
}
