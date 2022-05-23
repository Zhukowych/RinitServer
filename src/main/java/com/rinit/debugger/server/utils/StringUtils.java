package com.rinit.debugger.server.utils;

import java.util.ArrayList;
import java.util.List;

public class StringUtils {
	
	public static List<Integer> findAllOcurrences(String str, String substr){
		List<Integer> ocurences = new ArrayList<>();
		int previousOccurence = str.indexOf(substr);
		ocurences.add(previousOccurence);
		while(previousOccurence != -1) {
			previousOccurence = str.indexOf(substr, previousOccurence + 1);
			if (previousOccurence != -1)
				ocurences.add(previousOccurence);
		}
		
		return ocurences;
	}
	
}
