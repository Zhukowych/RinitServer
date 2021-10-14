package com.rinit.debugger.server.client;

import com.rinit.debugger.server.services.interfaces.IFileService;

public class RinitClient implements IClient {
	
	private IFileService fileServiceClient;
	
	public RinitClient(String serviceHost) {
		this.fileServiceClient = new FileServiceClient(serviceHost);
	}
	
	@Override
	public IFileService getFileService() {
		return this.fileServiceClient;
	}

}
