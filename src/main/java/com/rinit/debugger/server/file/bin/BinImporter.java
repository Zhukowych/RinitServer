package com.rinit.debugger.server.file.bin;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.rinit.debugger.server.utils.XMLReader;

public class BinImporter {
	
	private BinDriver bin;
	
	public BinImporter(BinDriver bin) {
		this.bin = bin;
	}
	
	public void parse(String content) {
		XMLReader reader = new XMLReader(content);
		Document document = reader.getDocument();
		this.bin.setName(reader.getTagValue("name", (Element)document.getElementsByTagName("bin").item(0)));
		this.bin.setBinLibraryPath(reader.getTagValue("libraryPath", (Element)document.getElementsByTagName("bin").item(0)));
		this.bin.setBinLibraryName(reader.getTagValue("libraryName", (Element)document.getElementsByTagName("bin").item(0)));
	}
	
		
}
