package com.rinit.debugger.server.file.bin;

import java.util.ArrayList;
import java.util.List;

public class BinLoadReportSerializer {
	
	private List<BinLoadReport> binsLoadReports = new ArrayList<BinLoadReport>();
	
	public void addLoadReport(BinLoadReport loadReport) {
		this.binsLoadReports.add(loadReport);
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("<loadReport>");
		for (BinLoadReport loadReport : this.binsLoadReports) {
			builder.append(loadReport.toString());
		}
		builder.append("</loadReport>");
		return builder.toString();
	}
	
}
