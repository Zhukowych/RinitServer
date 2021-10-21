package com.rinit.debugger.server.client;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.rinit.debugger.server.controller.urls.FileDriverControllerUrls;
import com.rinit.debugger.server.exception.ServiceException;
import com.rinit.debugger.server.file.library.LibraryDriver;
import com.rinit.debugger.server.services.interfaces.IFileDriverService;

public class FileDriverServiceClient implements IFileDriverService {

	private String serviceHost;
	private RestTemplate template = new RestTemplate();
	
	public FileDriverServiceClient(String serviceHost) {
		this.serviceHost = serviceHost;
	}
	
	@Override
	public List<String> getAvailableFileDrivers() throws ServiceException {
		return Arrays.asList(this.template.getForObject(this.serviceHost+FileDriverControllerUrls.GET_AVAILABLE_FILE_DRIVERS, String[].class));
	}

	@Override
	public LibraryDriver getDriverLibraryByName(String name) throws ServiceException {
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(this.serviceHost + FileDriverControllerUrls.GET_DRIVER_LIBRARY_BY_MAME);
	    builder.queryParam("name", name);
	    return this.template.getForObject(builder.toUriString(), LibraryDriver.class);
	}

}
