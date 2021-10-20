package com.rinit.debugger.server.services.interfaces;

import java.util.List;

import com.rinit.debugger.server.exception.ServiceException;
import com.rinit.debugger.server.file.library.LibraryDriver;


public interface IFileDriverService {

	public List<String> getAvailableFileDrivers() throws ServiceException;
	public LibraryDriver getDriverLibraryByName(String name) throws ServiceException;
	
}
