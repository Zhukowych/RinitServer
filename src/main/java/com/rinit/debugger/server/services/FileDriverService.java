package com.rinit.debugger.server.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.rinit.debugger.server.entity.IFileDriver;
import com.rinit.debugger.server.services.interfaces.IFileDriverService;

@Service
public class FileDriverService implements IFileDriverService {
	
	private Map<String, IFileDriver> fileDrivers = new HashMap<String, IFileDriver>();

	@Override
	public void registerDriver(String extention, IFileDriver driver) {
		this.fileDrivers.put(extention, driver);
	}
	
}
