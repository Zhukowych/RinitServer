package com.rinit.debugger.server.file.library;

import java.util.ArrayList;
import java.util.List;

import com.rinit.debugger.server.file.driver.PhysicalFileDriver;

public class LibraryExporter {
	
	private PhysicalFileDriver physicalFile;
	private List<ClassToLoadInfo> classesToLoad = new ArrayList<ClassToLoadInfo>();	
	
	public void setPhysicalFile(PhysicalFileDriver physicalFile) {
		this.physicalFile = physicalFile;
	}

	public void setClassesToLoad(List<ClassToLoadInfo> classesToLoad) {
		this.classesToLoad = classesToLoad;
	}

	public String export() {
		StringBuilder xmlBuilder = new StringBuilder("<library>");
		xmlBuilder.append(physicalFile.getContent());
		xmlBuilder.append("<classesToLoad>");
		for (ClassToLoadInfo classInfo : classesToLoad) {
			xmlBuilder.append("<loadClass>");
			xmlBuilder.append("<name>");
			xmlBuilder.append(classInfo.getName());
			xmlBuilder.append("</name>");
			xmlBuilder.append("<path>");
			xmlBuilder.append(classInfo.getName());
			xmlBuilder.append("</path>");
			xmlBuilder.append("</loadClass>");
		}
		xmlBuilder.append("</classesToLoad>");
		xmlBuilder.append("</library>");
		return xmlBuilder.toString();
	}
	
}
