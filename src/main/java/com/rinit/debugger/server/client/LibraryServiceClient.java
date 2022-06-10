package com.rinit.debugger.server.client;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.rinit.debugger.server.client.interfaces.ILibraryServiceClient;
import com.rinit.debugger.server.client.interfaces.IPhysicalFileServiceClient;
import com.rinit.debugger.server.controller.urls.LibraryControllerUrls;
import com.rinit.debugger.server.exception.ServiceException;
import com.rinit.debugger.server.file.library.LibraryDriver;
import com.rinit.debugger.server.file.library.LibraryNotFoundException;
import com.rinit.debugger.server.file.pfille.PhysicalFileDriver;
import com.rinit.debugger.server.services.interfaces.IPhysicalFileService;

public class LibraryServiceClient implements ILibraryServiceClient {

	private IClient client;
	private String serviceHost;
	private RestTemplate template = new RestTemplate();
	
	public LibraryServiceClient(IClient client, String serviceHost) {
		this.serviceHost = serviceHost;
		this.client = client;
	}
	
	@Override
	public LibraryDriver getLibraryByPathName(String path, String name) throws LibraryNotFoundException {
	    UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(this.serviceHost + LibraryControllerUrls.GET_LIBRARY_BY_PATH_NAME);
	    builder.queryParam("path", path);
	    builder.queryParam("name", name);
	    return this.template.getForObject(builder.toUriString(), LibraryDriver.class);
	}

	@Override
	public List<String> getLocatedPathes() {
		return Arrays.asList(this.template.getForObject(this.serviceHost+LibraryControllerUrls.GET_LIBRARIES_PATHES, String[].class));
	}

	@Override
	public List<String> getLibrariesNamesByPath(String path) throws ServiceException {
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(this.serviceHost + LibraryControllerUrls.GET_LIBRARY_NAMES_BY_PATH);
	    builder.queryParam("path", path);
	    return Arrays.asList(this.template.getForObject(builder.toUriString(), String[].class));
	}

	@Override
	public void autodiscover() {
		this.template.getForObject(this.serviceHost + LibraryControllerUrls.AUTODISCOVER_LIBRARIES, String.class);
	}

	@Override
	public LibraryDriver convertRemoteLibraryToLocal(LibraryDriver library) {
		IPhysicalFileServiceClient phsycialServiceClient = this.client.getPhysicalServiceClient();
		PhysicalFileDriver removePfile = library.getPhysicalFile();
		PhysicalFileDriver localPfile = phsycialServiceClient.downloadPhysicalFileByPath(removePfile.getFilePath());
		library.setPhysicalFile(localPfile);
		library.loadClasses();
		return library;
	}
	
}
