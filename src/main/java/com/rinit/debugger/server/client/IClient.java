package com.rinit.debugger.server.client;

import com.rinit.debugger.server.client.interfaces.ILibraryServiceClient;
import com.rinit.debugger.server.client.interfaces.IPhysicalFileServiceClient;
import com.rinit.debugger.server.services.interfaces.IFileDriverService;
import com.rinit.debugger.server.services.interfaces.IFileService;

public interface IClient {
	public IFileService getFileService();
	public IPhysicalFileServiceClient getPhysicalServiceClient();
	public ILibraryServiceClient getLibraryServiceClient();
	public IFileDriverService getFileDriverService();
}
