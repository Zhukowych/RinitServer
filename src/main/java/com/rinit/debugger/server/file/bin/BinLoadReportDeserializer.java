package com.rinit.debugger.server.file.bin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.rinit.debugger.server.utils.XMLReader;

public class BinLoadReportDeserializer {
	
	private XMLReader reader;
	private Document document;
	
	private Map<String, BinLoadReport> loadedBins = new HashMap<String, BinLoadReport>();
	
	public BinLoadReportDeserializer(String content) {
		this.reader = new XMLReader(content);
		this.document = this.reader.getDocument();
		this.parse();
	}
	
	public BinLoadReport getLoadedBinByName(String name) {
		return this.loadedBins.get(name);
	}
	
	public List<String> getBinNames(){
		List<String> names = new ArrayList<String>();
		for (BinLoadReport loadedBin : this.loadedBins.values()) {
			names.add(loadedBin.name);
		}
		return names;
	}
	
	private void parse() {
		NodeList loadedBins = document.getElementsByTagName("bin");
		for (int i = 0; i < loadedBins.getLength(); i++) {
			Node node = loadedBins.item(i);
			String binName = this.reader.getTagValue("name", (Element)node);
			String binLibraryPath = this.reader.getTagValue("libraryPath", (Element)node);
			String binLibraryName = this.reader.getTagValue("libraryName", (Element)node);
			this.loadedBins.put(binName, new BinLoadReport(binName, binLibraryPath, binLibraryName));
		}
	}
	
}
