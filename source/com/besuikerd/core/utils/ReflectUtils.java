package com.besuikerd.core.utils;

import static com.besuikerd.core.utils.functional.FunctionalUtils.functionGetClass;
import static com.besuikerd.core.utils.functional.FunctionalUtils.map;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.besuikerd.core.BLogger;
import com.besuikerd.core.utils.functional.Predicate;
import com.google.common.annotations.Beta;
import com.google.common.primitives.Primitives;

public class ReflectUtils {
	
	public static interface Invokable<E>{
		public E invoke();
	}
	
	public static <E> Invokable<E> invokable(final Method m, final Object o, final Class<E> returnType, final Object... args){
		return m == null ? null : new Invokable<E>() {
			
			@Override
			public E invoke() {
				if(m.getParameterTypes().length <= args.length && returnType == null || m.getReturnType().equals(returnType)){
					try {
						return (E) m.invoke(o, Arrays.copyOfRange(args, 0, m.getParameterTypes().length));
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
				return null;
			}
		};
	}
	
	public static Invokable<Void> invokable(final Method m, final Object o, final Object... args){
		return invokable(m, o, null, args);
	}
	
	public static <E> E invoke(Invokable<E> invokable){
		if(invokable != null){
			return invokable.invoke();
		}
		return null;
	}
	
	
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
	
	public static Set<Method> getMatchingMethods(Object o, String functionName){
		Set<Method> result = new HashSet<Method>();
		for(Method m : o.getClass().getMethods()){
			if(m.getName().equals(functionName)){
				result.add(m);
			}
		}
		return result;
	}
	
	public static Method findPartialMatchingMethod(final Object o, Set<Method> methods, Class<?> returnType, final Object... args){
		Method bestMethod = null;
		for(Method m : methods){
			Class<?>[] pTypes = m.getParameterTypes();
			if(args.length >= pTypes.length){
				
				boolean match = pTypes.length <= args.length && returnType == null || returnType.equals(m.getReturnType());
				
				for(int i = 0 ; i < pTypes.length && match ; i++){
					Class<?> argCls = Primitives.unwrap(args[i].getClass());
					Class<?> paramCls = Primitives.unwrap(pTypes[i]);
					match = paramCls.isAssignableFrom(argCls);
				}
				
				if(match){
					if(bestMethod == null || pTypes.length > bestMethod.getParameterTypes().length){
						bestMethod = m;
					} else if(pTypes.length == bestMethod.getParameterTypes().length){
						int compScore = 0;
						int bestScore = 0;
						Class<?>[] bestTypes = bestMethod.getParameterTypes();
						
						//calculate competing method score
						for(int i = 0 ; i < pTypes.length ; i++){
							if(bestTypes[i].isAssignableFrom(pTypes[i])){
								compScore ++;
							}
						}
						
						//calculate current best method score
						for(int i = 0 ; i < pTypes.length ; i++){
							if(pTypes[i].isAssignableFrom(bestTypes[i])){
								bestScore ++;
							}
						}
						
						if(compScore > bestScore){
							bestMethod = m;
						}
						
						
					}
					
				}
			}
		}
		return bestMethod;
	}
	
	/**
	 * find the method in Object o that matches the given function name and (partially) matches the given arguments. The best match (if any) will be returned
	 * @param o
	 * @param functionName
	 * @param args
	 * @return
	 */
	public static Method findPartialMatchingMethod(final Object o, String functionName, Class<?> returnType, final Object... args){
		return findPartialMatchingMethod(o, getMatchingMethods(o, functionName), returnType, args);
	}
	
	public static Method findPartialMatchingMethod(final Object o, String functionName, final Object... args){
		return findPartialMatchingMethod(o, functionName, null, args);
	}
	
	public static <E> Invokable<E> getPartialMatchingInvokable(final Object o, Set<Method> methods, Class<E> returnType, final Object... args){
		return invokable(findPartialMatchingMethod(o, methods, returnType, args), o, returnType, args);
	}
	
	public static Invokable<Void> getPartialMatchingInvokable(final Object o, Set<Method> methods, final Object... args){
		return getPartialMatchingInvokable(o, methods, null, args);
	}
	
	public static <E> Invokable<E> getPartialMatchingInvokable(final Object o, String functionName, final Class<E> returnType, final Object... args){
		return getPartialMatchingInvokable(o, getMatchingMethods(o, functionName), returnType, args);
	}
	
	public static Invokable<Void> getPartialMatchingInvokable(final Object o, String functionName, final Object... args){
		return getPartialMatchingInvokable(o, functionName, null, args);
	}
	
	public static <E> E invokePartialMatchingMethod(Object o, String functionName, Class<E> returnType, Object... args){
		return invoke(getPartialMatchingInvokable(o, functionName, returnType, args));
	}
	
	public static void invokePartialMatchingMethod(Object o, String functionName, Object... args){
		invokePartialMatchingMethod(o, functionName, null, args);
	}
	
	public static <E extends Annotation> Set<Method> getAnnotatedMethods(Object o, Class<E> annotation, Predicate<E> predicate){
		Set<Method> methods = new HashSet<Method>();
		for(Method m : o.getClass().getMethods()){
			if(m.isAnnotationPresent(annotation) && predicate.eval(m.getAnnotation(annotation))){
				methods.add(m);
			}
		}
		System.out.printf("methods: %s\n", methods);
		return methods;
	}
	
	public static <E extends Annotation> Set<Method> getAnnotatedMethods(Object o, Class<E> annotation){
		return getAnnotatedMethods(o, annotation, Predicate.TRUE);
	}
	
	public static <E, A extends Annotation> Invokable<E> getBestMatchingAnnotatedMethodInvokable(Object o, Class<A> annotation, Predicate<A> predicate, Class<E> returnType, Object... args){
		return invokable(findPartialMatchingMethod(o, getAnnotatedMethods(o, annotation, predicate), returnType, args), o, returnType, args);
	}
	
	public static <E, A extends Annotation> Invokable<E> getBestMatchingAnnotatedMethodInvokable(Object o, Class<A> annotation, Class<E> returnType, Object... args){
		return getBestMatchingAnnotatedMethodInvokable(o, annotation, Predicate.TRUE, returnType, args);
	}
	
	public static <A extends Annotation> Invokable<Void> getBestMatchingAnnotatedMethodInvokable(Object o, Class<A> annotation, Predicate<A> predicate, Object... args){
		return getBestMatchingAnnotatedMethodInvokable(o, annotation, predicate, null, args);
	}
	
	public static <A extends Annotation> Invokable<Void> getBestMatchingAnnotatedMethodInvokable(Object o, Class<A> annotation, Object... args){
		return getBestMatchingAnnotatedMethodInvokable(o, annotation, (Class) null, args);
	}
	
	public static <E, A extends Annotation> E invokeBestMatchingAnnotatedMethod(Object o, Class<A> annotation, Predicate<A> predicate, Class<E> returnType, Object... args){
		return invoke(getBestMatchingAnnotatedMethodInvokable(o, annotation, predicate, returnType, args));
	}
	
	public static <A extends Annotation> void invokeBestMatchingAnnotatedMethod(Object o, Class<A> annotation, Predicate<A> predicate, Object... args){
		invokeBestMatchingAnnotatedMethod(o, annotation, predicate, null, args);
	}
}
