package com.rinit.debugger.server.utils;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class XMLReader {
	
	private Document document;
	private String xml;
	
	public XMLReader(String xml) {
		this.xml = xml;
		this.document = this.getDocument();
	}
	
	public boolean isOk() {
		if (this.document != null)
			return true;
		else 
			return false;
	}
	
	public String nodeToString(Node node) {
	    StringWriter sw = new StringWriter();
	    try {
	      Transformer t = TransformerFactory.newInstance().newTransformer();
	      t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
	      t.setOutputProperty(OutputKeys.INDENT, "yes");
	      t.transform(new DOMSource(node), new StreamResult(sw));
	    } catch (TransformerException te) {
	      System.out.println("nodeToString Transformer Exception");
	    }
	    return sw.toString();
	}
	
	public String getTagValue(String tagName, Element element) {
        NodeList list = element.getElementsByTagName(tagName);
        if (list != null && list.getLength() > 0) {
            NodeList subList = list.item(0).getChildNodes();

            if (subList != null && subList.getLength() > 0) {
                return subList.item(0).getNodeValue();
            }
        }

        return null;
    }
	
	public String getTagValueByName(String tagName, String elementName) {
		return this.getTagValue(tagName, this.getElementByName(elementName));
	}
	
	public String[][] getTableData(String parentElement, String[] tagNames){
		NodeList nodes = this.document.getElementsByTagName(parentElement);
		String[][] data = new String[nodes.getLength()][tagNames.length];
		for (int row = 0; row < nodes.getLength(); row++) {
			for (int col = 0; col < tagNames.length; col++) {
				data[row][col] = this.getTagValue(tagNames[col], (Element)nodes.item(row));
			}
		}
		return data;
	}
	
	public Element getElementByName(String tagName){
		return (Element)this.document.getElementsByTagName(tagName).item(0);
	}
	
	public String innerXml(String tag) {
		String openTag = String.format("<%s>", tag);
		String closeTag = String.format("</%s>", tag);
		int openTagInd = xml.indexOf(openTag);
		int closeTagInd = xml.indexOf(closeTag);
		return xml.substring(openTagInd, closeTagInd+closeTag.length());
	}
	
	public List<String> innerXmls(String tag){
		List<String> innerXmls = new ArrayList<String>();
		String openTag = String.format("<%s>", tag);
		String closeTag = String.format("</%s>", tag);
		List<Integer> openTagOcurrences = StringUtils.findAllOcurrences(this.xml, openTag);
		List<Integer> closeTagOcurrences = StringUtils.findAllOcurrences(this.xml, closeTag);
		for (int i = 0; i<openTagOcurrences.size(); i++) {
			if (openTagOcurrences.get(i) != -1 && closeTagOcurrences.get(i) != -1) 
				innerXmls.add(this.xml.substring(openTagOcurrences.get(i), closeTagOcurrences.get(i) + closeTag.length()));
		}
		return innerXmls;
	}
	
	public Document getDocument() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        if(this.xml.isEmpty()) 
        	return null;
        try {
            builder = factory.newDocumentBuilder();
            this.document = builder.parse(new InputSource(new StringReader(this.xml)));
            this.document.getDocumentElement().normalize();
            return this.document;
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
		return null;
	}
	
	
	
	
}
