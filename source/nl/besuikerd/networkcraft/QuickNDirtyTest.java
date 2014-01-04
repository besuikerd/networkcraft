package nl.besuikerd.networkcraft;

import static nl.besuikerd.core.utils.FunctionalUtils.foldl;
import static nl.besuikerd.core.utils.FunctionalUtils.foldr;
import static nl.besuikerd.core.utils.FunctionalUtils.functionAny;
import static nl.besuikerd.core.utils.FunctionalUtils.functionApplyFunction;
import static nl.besuikerd.core.utils.FunctionalUtils.map;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.tileentity.TileEntity;
import nl.besuikerd.core.gui.event.Event;
import nl.besuikerd.core.utils.BitUtils;
import nl.besuikerd.core.utils.FunctionalUtils.ABAFunction;
import nl.besuikerd.core.utils.FunctionalUtils.ABBFunction;
import nl.besuikerd.core.utils.ReflectUtils;
import nl.besuikerd.core.utils.collection.SafeConstrainedMap;
import nl.besuikerd.core.utils.collection.SmallerThanMapConstraint;

import com.google.common.base.Function;

public class QuickNDirtyTest {
	
	public static void main(String[] args) {
		functionalTest();
		bitUtilsTest();	

		Map<Object, Integer> m = new HashMap<Object, Integer>();
		m = SafeConstrainedMap.create(m, new SmallerThanMapConstraint<Object, Integer>(m));
		m.put(0, 5);
		m.put(0, 1);
		m.put(0, 6);
		System.out.println(m);
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
}
