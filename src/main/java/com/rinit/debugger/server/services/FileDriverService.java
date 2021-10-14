package com.rinit.debugger.server.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.rinit.debugger.server.entity.IFileDriver;
import com.rinit.debugger.server.file.library.LibraryDriver;
import com.rinit.debugger.server.services.interfaces.IFileDriverService;

@Service
public class FileDriverService implements IFileDriverService {
	
	private Map<String, Class<?>> fileDrivers = new HashMap<String, Class<?>>();

	public FileDriverService() {
		Class<?> libraryDriver = LibraryDriver.class;
		fileDrivers.put("lib", libraryDriver);
	}
	
	@Override
	public void registerDriver(String extention, Class<IFileDriver> driver) {
		this.fileDrivers.put(extention, driver);
	}

	
	
}
