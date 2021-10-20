package com.rinit.debugger.server.file.library;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.rinit.debugger.server.utils.XMLReader;

public class LibraryLoadReportDeserializer {
	
	private XMLReader reader;
	private Document document;
	
	private Map<String, List<String>> librariesByPathes = new HashMap<String, List<String>>();
	
	public LibraryLoadReportDeserializer(String content) {
		this.reader = new XMLReader(content);
		this.document = this.reader.getDocument();
		this.parse();
	}
	
	public List<String> getLocatedPathes() {
		List<String> keys = new ArrayList<String>();
		keys.addAll(this.librariesByPathes.keySet());
		return keys;
	}
	
	public List<String> getLibrariesNamesByPath(String name) {
		return this.librariesByPathes.get(name);
	}
	
	public boolean isExistLibrary(String path, String name) {
		List<String> librariesByPath = this.librariesByPathes.get(path);
		if (librariesByPath == null) {
			return false;
		}

		if (librariesByPath.contains(name)) {
			return true;
		} else {
			return false;
		}
	}
	
	private void parse() {
		NodeList classesToLoad = document.getElementsByTagName("libraryReport");
		for (int i = 0; i < classesToLoad.getLength(); i++) {
			Node node = classesToLoad.item(i);
			String libraryName = this.reader.getTagValue("libraryName", (Element)node);
			String libraryPath = this.reader.getTagValue("libraryPath", (Element)node);
			this.addExistingLibraries(libraryPath, libraryName);		
		}
	}
	
	private void addExistingLibraries(String path, String name) {
		List<String> librariesByPath = this.librariesByPathes.get(path);
		
		if(librariesByPath == null) {
			librariesByPath = new ArrayList<>();
			librariesByPath.add(name);
			this.librariesByPathes.put(path, librariesByPath);
		} else {
			librariesByPath.add(name);
		}
		
	}
	
}
