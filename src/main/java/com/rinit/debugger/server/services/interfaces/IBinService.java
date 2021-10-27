package com.rinit.debugger.server.services.interfaces;

import java.util.List;

public interface IBinService {
	
	public void autodiscoverBins();
	public List<String> getAwailableBins();
	public void runBinWithName(String name);
	public void killProcess(String kill);
	
}
