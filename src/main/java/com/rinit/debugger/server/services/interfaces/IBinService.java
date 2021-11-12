package com.rinit.debugger.server.services.interfaces;

import java.util.List;
import java.util.Map;

public interface IBinService {
	
	public void autodiscoverBins();
	public List<String> getAwailableBinsNames();
	public Map<String, Class<?>> getBins();
	
}
