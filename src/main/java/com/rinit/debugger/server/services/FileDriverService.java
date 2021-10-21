package com.rinit.debugger.server.services;

import java.util.List;

import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rinit.debugger.server.exception.ServiceException;
import com.rinit.debugger.server.file.library.LibraryDriver;
import com.rinit.debugger.server.file.library.LibraryNotFoundException;
import com.rinit.debugger.server.services.interfaces.IFileDriverService;
import com.rinit.debugger.server.services.library.LibraryService;

@Service
public class FileDriverService implements IFileDriverService {
	
	private final static String FILE_DRIVERS_PATH = "/lib/ext/";
	
	@Autowired
	private LibraryService libraryService;

	@Override
	public List<String> getAvailableFileDrivers() throws ServiceException {
		try {
			return this.libraryService.getLibrariesNamesByPath(FILE_DRIVERS_PATH);
		} catch (ServiceException e) {
			throw new ServiceException("There are no drivers");
		}
	}

	@Override
	public LibraryDriver getDriverLibraryByName(String name) throws ServiceException {
		try {
			return this.libraryService.getLibraryByPathName(FILE_DRIVERS_PATH, name);
		} catch (LibraryNotFoundException e) {
			throw new ServiceException(String.format("there are no drivers with name %s", name));
		}
	}
		
	
}
