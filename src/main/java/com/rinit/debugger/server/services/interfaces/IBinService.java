package com.rinit.debugger.server.services.interfaces;

import java.util.List;

import com.rinit.debugger.server.dto.FileDTO;
import com.rinit.debugger.server.exception.ServiceException;

public interface IBinService {
	
	public void autodiscoverBins();
	public List<String> getAwailableBinsNames();
	public FileDTO runBinWithName(String name, String[] params) throws ServiceException;
	public void killProcess(String kill);
	
}
