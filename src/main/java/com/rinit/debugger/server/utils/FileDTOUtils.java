package com.rinit.debugger.server.utils;

import java.util.ArrayList;
import java.util.List;

import com.rinit.debugger.server.dto.FileDTO;

public class FileDTOUtils {
	
	public static List<String> getChildrenPathsList(List<FileDTO> dtos){
		List<String> paths = new ArrayList<String>();
		for(FileDTO dto : dtos) {
			paths.add(dto.getChildrenPath());
		}
		return paths;
	}
}
