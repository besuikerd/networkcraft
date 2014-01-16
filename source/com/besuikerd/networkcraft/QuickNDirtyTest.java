package com.besuikerd.networkcraft;

import static com.besuikerd.core.utils.functional.FunctionalUtils.foldl;
import static com.besuikerd.core.utils.functional.FunctionalUtils.foldr;
import static com.besuikerd.core.utils.functional.FunctionalUtils.functionAny;
import static com.besuikerd.core.utils.functional.FunctionalUtils.functionApplyFunction;
import static com.besuikerd.core.utils.functional.FunctionalUtils.map;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import net.minecraft.tileentity.TileEntity;

import com.besuikerd.core.utils.BitUtils;
import com.besuikerd.core.utils.ReflectUtils;
import com.besuikerd.core.utils.ReflectUtils.Invokable;
import com.besuikerd.core.utils.functional.FunctionalUtils.ABAFunction;
import com.besuikerd.core.utils.functional.FunctionalUtils.ABBFunction;
import com.besuikerd.core.utils.functional.Predicate;
import com.besuikerd.networkcraft.dv.INode;
import com.google.common.base.Function;
import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;

public class QuickNDirtyTest {
	
	public static void main(String[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		functionalTest();
		bitUtilsTest();	
		reflectionTest();
	}
	
	static class Bla{
		final String x;

		public Bla(String x) {
			this.x = x;
		
		}
		
		public String getX() {
			return x;
		}
	}
	
	public static void recurs(int n){
		if(n > 0){
			recurs(n - 1);
		}
	}
	
	static class Hoi extends TileEntity{
		public Hoi(int n) {
			xCoord = n;
			yCoord = n;
			zCoord = n;
		}
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
	
	private static void reflectionTest() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Methodeer m = new Methodeer();
		
		Invokable i = ReflectUtils.getBestMatchingAnnotatedMethodInvokable(m, MeFirst.class, new Predicate<MeFirst>() {
			public boolean eval(MeFirst input) {
				System.out.println(input);
				return input.enabled();
			};
		}, "a", "b");
		
		if(i != null){
			i.invoke();
		} else{
			ReflectUtils.invokePartialMatchingMethod(m, "a", void.class, new Object().toString(), "ello!", "dsas");
		}
	}
	
	public static class Methodeer{
		
		@MeFirst
		public void a(){
			System.out.println("no args");
		}
		
		public void a(String s){
			System.out.printf("string: %s\n", s);
		}
		
		public void a(Object o){
			System.out.printf("object: %s\n", o);
		}
		
		public void a(String s, Object o){
			a(s);
			a(o);
		}
		
		public String a(Object o, String s){
			System.out.printf("returning: %s\n", s);
			return s;
		}
		
		
		public void a(Object o, Object o2){
			a(o);
			a(o2);
		}
		
		@MeFirst(enabled = false)
		public void a(String s, String s2){
			a(s);
			a(s2);
		}
		
		public void a(Object o, Object o2, Object o3){
			a(o);
			a(o2);
			a(o3);
		}
	}
	
	@Retention(RetentionPolicy.RUNTIME)
	static @interface MeFirst{
		boolean enabled() default true;
	} 
}
