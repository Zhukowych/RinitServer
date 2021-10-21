package com.rinit.debugger.server.client;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.rinit.debugger.server.controller.urls.LibraryControllerUrls;
import com.rinit.debugger.server.exception.ServiceException;
import com.rinit.debugger.server.file.library.LibraryDriver;
import com.rinit.debugger.server.file.library.LibraryNotFoundException;
import com.rinit.debugger.server.services.interfaces.ILibraryService;

public class LibraryServiceClient implements ILibraryService {

	private String serviceHost;
	private RestTemplate template = new RestTemplate();
	
	public LibraryServiceClient(String serviceHost) {
		this.serviceHost = serviceHost;
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
	public void checkLibraries() {
		// TODO Auto-generated method stub
		
	}

}
