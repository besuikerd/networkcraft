package com.besuikerd.core.utils;

import static com.besuikerd.core.utils.FunctionalUtils.functionGetClass;
import static com.besuikerd.core.utils.FunctionalUtils.map;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.besuikerd.core.BLogger;

public class ReflectUtils {
	public static void invoke(Object obj, String name, Object... params){
		try {
			Method m = obj.getClass().getDeclaredMethod(name);
			m.setAccessible(true);
			m.invoke(obj, params);
		} catch (Exception e) {
			BLogger.error("reflection failed for %s: %s", obj.getClass().getName(), e.toString());
		}
	}
	
	public static <E> E newInstance(Class<E> cls, Object... params){
		E instance = null;
		Class[] classes = map(Class.class, params, functionGetClass);
		
		try {
			
			for(Constructor<?> c : cls.getDeclaredConstructors()){
				
				boolean matchingConstructor = c.getParameterTypes().length == classes.length;
				Class<?>[] tClasses = c.getParameterTypes();
				for(int i = 0 ; i < tClasses.length && i < classes.length && matchingConstructor ; i++){
					Class<?> tClass = tClasses[i];
					if(!tClass.isAssignableFrom(classes[i])){
						matchingConstructor = false;
					}
				}
				if(matchingConstructor){
					instance = (E) c.newInstance(params);
				}
				break;
			}
		//} catch (NoSuchMethodException e) {
			//BLogger.error("No constructor exists with correct parameters for %s, does a constructor exist with the following parameters? %s", cls.getName(), Arrays.toString(map(String.class, map(Class.class, params, functionGetClass), functionApplyFunction("getName", Class.class, String.class))));
		} catch (SecurityException e) {
			BLogger.error("Not allowed to instantiate constructor, security issues");
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
