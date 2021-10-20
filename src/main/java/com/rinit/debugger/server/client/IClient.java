package com.rinit.debugger.server.client;

import com.rinit.debugger.server.services.interfaces.IFileService;
import com.rinit.debugger.server.services.interfaces.IPhysicalFileService;

public interface IClient {
	public IFileService getFileService();
	public IPhysicalFileService getPhysicalServiceClient();
}
