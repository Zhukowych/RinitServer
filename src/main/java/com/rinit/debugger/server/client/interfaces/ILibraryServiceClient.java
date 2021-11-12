package com.rinit.debugger.server.client.interfaces;

import com.rinit.debugger.server.file.library.LibraryDriver;
import com.rinit.debugger.server.services.interfaces.ILibraryService;

public interface ILibraryServiceClient extends ILibraryService {
	
	public LibraryDriver convertRemoteLibraryToLocal(LibraryDriver library);
	
}
