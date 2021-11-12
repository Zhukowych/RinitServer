package com.rinit.debugger.server.client;

import com.rinit.debugger.server.client.interfaces.ILibraryServiceClient;
import com.rinit.debugger.server.client.interfaces.IPhysicalFileServiceClient;
import com.rinit.debugger.server.services.interfaces.IBinService;
import com.rinit.debugger.server.services.interfaces.IFileDriverService;
import com.rinit.debugger.server.services.interfaces.IFileService;

public class RinitClient implements IClient {
	
	private IFileService fileServiceClient;
	private IPhysicalFileServiceClient physicalServiceClient;
	private ILibraryServiceClient libraryServiceClient;
	private IFileDriverService fileDriverService;
	private IBinService binService;
	
	public RinitClient(String serviceHost) {
		this.fileServiceClient = new FileServiceClient(serviceHost);
		this.physicalServiceClient = new PhysicalFileServiceClient(serviceHost);
		this.libraryServiceClient = new LibraryServiceClient(this, serviceHost);
		this.fileDriverService = new FileDriverServiceClient(this, serviceHost);
		this.binService = new BinServiceClient(serviceHost);
	}
	
	@Override
	public IFileService getFileService() {
		return this.fileServiceClient;
	}

	@Override
	public IPhysicalFileServiceClient getPhysicalServiceClient() {
		return physicalServiceClient;
	}

	@Override
	public ILibraryServiceClient getLibraryServiceClient() {
		return this.libraryServiceClient;
	}

	@Override
	public IFileDriverService getFileDriverService() {
		return this.fileDriverService;
	}

	public IBinService getBinService() {
		return binService;
	}

}
