package nl.besuikerd.networkcraft.core.utils;

import static nl.besuikerd.networkcraft.core.utils.FunctionalUtils.functionApplyFunction;
import static nl.besuikerd.networkcraft.core.utils.FunctionalUtils.functionGetClass;
import static nl.besuikerd.networkcraft.core.utils.FunctionalUtils.map;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import nl.besuikerd.networkcraft.core.NCLogger;

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
		Class[] classes = map(Class.class, params, functionGetClass);
		
		try {
			Constructor c = cls.getDeclaredConstructor(classes);
			instance = (E) c.newInstance(params);
		} catch (NoSuchMethodException e) {
			NCLogger.error("No constructor exists with correct parameters for %s, does a constructor exist with the following parameters? %s", cls.getName(), Arrays.toString(map(String.class, map(Class.class, params, functionGetClass), functionApplyFunction("getName", Class.class, String.class))));
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
	
	
}
