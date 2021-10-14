package com.rinit.debugger.server.file.executable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.rinit.debugger.server.utils.XMLReader;

public class ExecutableImporter {

	private String libraryPath;
	private String libraryName;
	
	public void parse(String content) {
		XMLReader reader = new XMLReader(content);
		Document document = reader.getDocument();
		this.libraryPath = reader.getTagValue("libraryPath", (Element)document.getElementsByTagName("library").item(0));
		this.libraryName = reader.getTagValue("libraryName", (Element)document.getElementsByTagName("library").item(0));
	}
	
	public String getLibraryName() {
		return this.libraryName;
	}
	
	public String getLibraryPath() {
		return this.libraryPath;
	}
		
}
