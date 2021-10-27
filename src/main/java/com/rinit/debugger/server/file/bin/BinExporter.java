package com.rinit.debugger.server.file.bin;

public class BinExporter {
	
	private BinDriver bin;
	
	public BinExporter(BinDriver bin) {
		this.bin = bin;
	}
	
	public String export() {
		StringBuilder builder = new StringBuilder();
		builder.append("<bin>");
		builder.append("<name>");
		builder.append(this.bin.getName());
		builder.append("</name>");
		builder.append("<libraryPath>");
		builder.append(this.bin.getBinLibraryPath());
		builder.append("</libraryPath>");
		builder.append("<libraryName>");
		builder.append(this.bin.getBinLibraryName());
		builder.append("</libraryName>");
		builder.append("</bin>");
		return builder.toString();
	}
	
}
