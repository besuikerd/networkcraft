package nl.besuikerd.networkcraft.core.utils;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import nl.besuikerd.networkcraft.core.NCLogger;

import com.google.common.base.Function;

public class ReflectUtils {
	public static void invoke(Object obj, String name, Object... params){
		try {
			Method m = obj.getClass().getDeclaredMethod(name);
			m.setAccessible(true);
			m.invoke(obj, params);
		} catch (Exception e) {
			NCLogger.error("reflection failed for %s: %s", obj.getClass().getName(), e.toString());
		}
	}
	
	public static <E> E newInstance(Class<E> cls, Object... params){
		E instance = null;
		Class[] classes = map(functionGetClass, Class.class, params);
		
		try {
			Constructor c = cls.getDeclaredConstructor(classes);
			instance = (E) c.newInstance(params);
		} catch (NoSuchMethodException e) {
			NCLogger.error("No constructor exists with correct parameters for %s, does a constructor exist with the following parameters? %s", cls.getName(), Arrays.toString(map(functionApplyFunction("getName", Class.class, String.class), String.class, map(functionGetClass, Class.class, params))));
		} catch (SecurityException e) {
			NCLogger.error("Not allowed to instantiate constructor, security issues");
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return instance;
	}
	
	/**
	 * function instance that returns the Class of the Object given as an argument(i.e. obj.getClass())
	 */
	public static final Function<Object, Class> functionGetClass = new Function<Object, Class>() {
		@Override
		public Class apply(Object input) {
			return input.getClass();
		}
	};
	
	/**
	 * Applies a function that exists in the given object and returns the result of executing that function
	 * 
	 * precondition: (Class c), (Function f) => [Ec Ax, y, c(x) == c(y)] && [Ec.f, f.getName() == name ] (all classes are equal in the given array) and (there exists a function f within c with the given name)
	 */
	public static final <A,B> Function<A, B> functionApplyFunction(final String name, Class<A> from, Class<B> to, final Object... params){
				
		return new Function<A, B>(){
			private Method m;
			
			@Override
			public B apply(A input) {
				B result = null;
				
				if(m == null){
					try{
						m = input.getClass().getDeclaredMethod(name);
					} catch (NoSuchMethodException e) {
						NCLogger.error("Method %s does not exist in class %s", name, input.getClass().getName());
					} catch (SecurityException e) {
						NCLogger.error("not allowed to obtain fields from classes");
						e.printStackTrace();
					}
				}
				
				try {
					result = (B) m.invoke(input, params);
				} catch (SecurityException e) {
					NCLogger.error("not allowed to obtain fields from classes");
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				
				return result;
			}
		};
	};
	
	public static final <A, B> B[] map(Function<A,B> f, Class<B> b, A[] input){
		B[] result = (B[]) Array.newInstance(b, input.length);
		for(int i = 0 ; i < result.length ; i ++){
			result[i] = f.apply(input[i]);
		}
		return result;
	}
}
