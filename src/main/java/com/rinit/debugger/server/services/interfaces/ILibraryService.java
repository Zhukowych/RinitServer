package com.rinit.debugger.server.services.interfaces;

import java.util.List;

import javax.sql.rowset.serial.SerialException;

import com.rinit.debugger.server.exception.ServiceException;
import com.rinit.debugger.server.file.library.LibraryDriver;
import com.rinit.debugger.server.file.library.LibraryNotFoundException;

public interface ILibraryService {
	
	public LibraryDriver getLibraryByPathName(String path, String name) throws LibraryNotFoundException;
	public List<String> getLocatedPathes();
	public List<String> getLibrariesNamesByPath(String path) throws ServiceException;
	
	public void autodiscover();

}
