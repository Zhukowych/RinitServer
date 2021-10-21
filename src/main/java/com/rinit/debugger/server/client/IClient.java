package com.rinit.debugger.server.client;

import com.rinit.debugger.server.services.interfaces.IFileDriverService;
import com.rinit.debugger.server.services.interfaces.IFileService;
import com.rinit.debugger.server.services.interfaces.ILibraryService;
import com.rinit.debugger.server.services.interfaces.IPhysicalFileService;

public interface IClient {
	public IFileService getFileService();
	public IPhysicalFileService getPhysicalServiceClient();
	public ILibraryService getLibraryServiceClient();
	public IFileDriverService getFileDriverService();
}
