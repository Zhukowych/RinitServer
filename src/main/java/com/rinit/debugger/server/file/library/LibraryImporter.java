package com.rinit.debugger.server.file.library;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.rinit.debugger.server.file.driver.PhysicalFileDriver;
import com.rinit.debugger.server.utils.XMLReader;


public class LibraryImporter {
	
	private String content;
	private PhysicalFileDriver physicalFile;
	private List<ClassToLoadInfo> classesToLoad = new ArrayList<ClassToLoadInfo>();	
	
	public PhysicalFileDriver getPhysicalFile() {
		return physicalFile;
	}

	public List<ClassToLoadInfo> getClassesToLoad() {
		return classesToLoad;
	}

	
	public void parse(String content) {
		XMLReader reader = new XMLReader(content);
		Document document = reader.getDocument();
		this.physicalFile = PhysicalFileDriver.builder().build(); 
		this.physicalFile.fromContent(reader.innerXml("physicalFile"));
		this.parseClassesToLoad(reader, document);
	}
	
	
	private void parseClassesToLoad(XMLReader reader, Document document) {
		NodeList classesToLoad = document.getElementsByTagName("loadClass");
		for (int i = 0; i < classesToLoad.getLength(); i++) {
			Node node = classesToLoad.item(i);
			ClassToLoadInfo classInfo = ClassToLoadInfo.builder().name(
						reader.getTagValue("name", (Element)node)
					).path(
							reader.getTagValue("path", (Element)node)
							)
					.build();
			this.classesToLoad.add(classInfo);
		}
	}
	
	
	
	
	
}
