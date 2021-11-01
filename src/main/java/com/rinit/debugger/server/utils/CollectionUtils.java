package com.rinit.debugger.server.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CollectionUtils {
	
	public static<T> List<T> setToList(Set<T> set) {
		List<T> list = new ArrayList<T>();
		list.addAll(set);
		return list;
	}
}
