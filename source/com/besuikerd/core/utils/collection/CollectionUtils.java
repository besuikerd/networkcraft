package com.besuikerd.core.utils.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.base.Function;

public class CollectionUtils {
	public static int[] range(int start, int end){
		int[] result = new int[end - start + 1];
		for(int i = 0 ; i < result.length ; i++){
			result[i] = i + start;
		}
		return result;
	}
	
	public static <A> List<A> generateListFromFunction(int n, Function<Void, A> generator){
		List<A> list = new ArrayList(n);
		for(int i = 0 ; i < n ; i++){
			list.add(generator.apply(null));
		}
		return list;
	}
	
	public static <A> Collection<A> generateCollectionFromFunction(int n, Function<Void, A> generator){
		return generateListFromFunction(n, generator);
	}
}
