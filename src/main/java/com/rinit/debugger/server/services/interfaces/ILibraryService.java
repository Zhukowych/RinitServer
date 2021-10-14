package com.rinit.debugger.server.services.interfaces;

import com.rinit.debugger.server.file.library.LibraryDriver;
import com.rinit.debugger.server.file.library.LibraryNotFoundException;

public interface ILibraryService {
	
	public LibraryDriver getLibraryByPathName(String path, String name) throws LibraryNotFoundException;
	
	public void loadLibraries();

}
