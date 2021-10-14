package com.rinit.debugger.server.client;

import com.rinit.debugger.server.services.interfaces.IFileService;

public interface IClient {
	public IFileService getFileService();
}
