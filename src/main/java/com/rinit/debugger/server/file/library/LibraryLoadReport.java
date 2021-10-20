package com.rinit.debugger.server.file.library;

import java.util.ArrayList;
import java.util.List;

import com.rinit.debugger.server.file.driver.PhysicalFileDriver;

public class LibraryLoadReport {
	
	private String libraryName;
	private String libraryPath;
	private LibraryDriver library;
	private List<ClassToLoadInfo> loadedClasses = new ArrayList<ClassToLoadInfo>();
	private List<String> errors = new ArrayList<String>();
	
	public void setLibrary(LibraryDriver library) {
		this.library = library;
	}
	
	public void addLoadedClass(ClassToLoadInfo loadedClassInfo) {
		this.loadedClasses.add(loadedClassInfo);
	}
	
	public void addError(String error) {
		this.errors.add(error);
	}
	
	public String toString() {
		StringBuilder xmlBuilder = new StringBuilder("");
		xmlBuilder.append("<libraryReport>");
		xmlBuilder.append("<libraryName>");
		xmlBuilder.append(this.library.getName());
		xmlBuilder.append("</libraryName>");
		xmlBuilder.append("<libraryPath>");
		xmlBuilder.append(this.library.getPath());
		xmlBuilder.append("</libraryPath>");
		xmlBuilder.append(this.library.getContent());
		xmlBuilder.append("<loadedClasses>");
		this.addLoadedClasesTo(xmlBuilder);
		xmlBuilder.append("</loadedClasses>");
		if (this.errors.size() != 0) {
			xmlBuilder.append("<errors>");
			this.addErrorsTo(xmlBuilder);
			xmlBuilder.append("<errors/>");
		} else {
			xmlBuilder.append("<errors/>");	
		}
		xmlBuilder.append("</libraryReport>");
		return xmlBuilder.toString();
	}
	
	private void addLoadedClasesTo(StringBuilder xmlBuilder){
		for (ClassToLoadInfo loadedClass : loadedClasses) {
			xmlBuilder.append("<loadedClass>");
			xmlBuilder.append("<name>");
			xmlBuilder.append(loadedClass.getName());
			xmlBuilder.append("</name>");
			xmlBuilder.append("<path>");
			xmlBuilder.append(loadedClass.getPath());
			xmlBuilder.append("</path>");
			xmlBuilder.append("</loadedClass>");
		}	
	}
	
	private void addErrorsTo(StringBuilder xmlBuilder) {
		for(String error : this.errors) {
			xmlBuilder.append("<error>");
			xmlBuilder.append(error);
			xmlBuilder.append("</error>");
		}
	}
	
}
