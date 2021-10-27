package com.rinit.debugger.server.file.pfille;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.rinit.debugger.server.utils.XMLReader;

public class PhysicalFileImporter {
	
	private XMLReader reader;
	private Document document;
	private PhysicalFileDriver pfile;
	
	public PhysicalFileImporter(String content, PhysicalFileDriver pfile) {
		this.reader = new XMLReader(content);
		this.document = this.reader.getDocument();
		this.pfile = pfile;
	}
	
	public void parse() {
		Node node = this.document.getElementsByTagName("physicalFile").item(0);
		this.pfile.setFilePath(this.reader.getTagValue("physicalLocation", (Element)node));
	}
	
}
