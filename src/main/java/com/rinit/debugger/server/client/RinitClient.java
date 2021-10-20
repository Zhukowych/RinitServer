package com.rinit.debugger.server.client;

import com.rinit.debugger.server.services.interfaces.IFileService;
import com.rinit.debugger.server.services.interfaces.IPhysicalFileService;

public class RinitClient implements IClient {
	
	private IFileService fileServiceClient;
	private IPhysicalFileService physicalServiceClient;
	
	
	public RinitClient(String serviceHost) {
		this.fileServiceClient = new FileServiceClient(serviceHost);
		this.physicalServiceClient = new PhysicalFileServiceClient(serviceHost);
	}
	
	@Override
	public IFileService getFileService() {
		return this.fileServiceClient;
	}

	@Override
	public IPhysicalFileService getPhysicalServiceClient() {
		return physicalServiceClient;
	}

}
