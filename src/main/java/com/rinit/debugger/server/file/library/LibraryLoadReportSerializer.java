package com.rinit.debugger.server.file.library;

import java.util.ArrayList;
import java.util.List;

public class LibraryLoadReportSerializer {
	
	private List<LibraryLoadReport> librariesReports = new ArrayList<LibraryLoadReport>();
	
	public void addLibraryReport(LibraryLoadReport liraryReport) {
		this.librariesReports.add(liraryReport);
	}
	
	public String toString() {
		StringBuilder xmlBuilder = new StringBuilder("");
		xmlBuilder.append("<librariesReports>");
		for(LibraryLoadReport libraryReport : librariesReports) {
			xmlBuilder.append(libraryReport.toString());
		}
		xmlBuilder.append("</librariesReports>");
		return xmlBuilder.toString();
	}
}
