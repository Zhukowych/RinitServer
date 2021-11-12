package com.rinit.debugger.server.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CollectionUtils {
	
	public static<T> List<T> setToList(Set<T> set) {
		List<T> list = new ArrayList<T>();
		list.addAll(set);
		return list;
	}
	
	public static<T> Map<T, T> ndArrayToMap(T[][] array) {
		Map<T, T> map = new HashMap<>();	
		for (int i = 0; i< array.length; i++) {
			map.put(array[i][0], array[i][1]);
		}
		return map;
	}
}
