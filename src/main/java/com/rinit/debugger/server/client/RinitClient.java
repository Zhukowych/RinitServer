package com.rinit.debugger.server.client;

import com.rinit.debugger.server.services.interfaces.IFileDriverService;
import com.rinit.debugger.server.services.interfaces.IFileService;
import com.rinit.debugger.server.services.interfaces.ILibraryService;
import com.rinit.debugger.server.services.interfaces.IPhysicalFileService;

public class RinitClient implements IClient {
	
	private IFileService fileServiceClient;
	private IPhysicalFileService physicalServiceClient;
	private ILibraryService libraryServiceClient;
	private IFileDriverService fileDriverService;
	
	public RinitClient(String serviceHost) {
		this.fileServiceClient = new FileServiceClient(serviceHost);
		this.physicalServiceClient = new PhysicalFileServiceClient(serviceHost);
		this.libraryServiceClient = new LibraryServiceClient(serviceHost);
		this.fileDriverService = new FileDriverServiceClient(serviceHost);
	}
	
	@Override
	public IFileService getFileService() {
		return this.fileServiceClient;
	}

	@Override
	public IPhysicalFileService getPhysicalServiceClient() {
		return physicalServiceClient;
	}

	@Override
	public ILibraryService getLibraryServiceClient() {
		return this.libraryServiceClient;
	}

	@Override
	public IFileDriverService getFileDriverService() {
		return this.fileDriverService;
	}

}
