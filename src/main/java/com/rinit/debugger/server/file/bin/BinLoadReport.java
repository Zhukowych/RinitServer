package com.rinit.debugger.server.file.bin;

public class BinLoadReport {
	
	public String name;
	public String libraryPath;
	public String libraryName;
	public String error;

	public BinLoadReport() {}
	
	public BinLoadReport(String name, String libraryPath, String libraryName) {
		this.name = name;
		this.libraryPath = libraryPath;
		this.libraryName = libraryName;
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("<bin>");
		builder.append("<name>");
		builder.append(this.name);
		builder.append("</name>");
		builder.append("<libraryPath>");
		builder.append(this.libraryPath);		
		builder.append("</libraryPath>");
		builder.append("<libraryName>");
		builder.append(this.libraryName);
		builder.append("</libraryName>");
		if(this.error != null) {
			builder.append("</error>");
			builder.append(this.error);
			builder.append("</error>");		
		} else {
			builder.append("<error/>");
		}
		builder.append("</bin>");
		return builder.toString();
	}
	
}
