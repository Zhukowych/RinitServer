package com.rinit.debugger.server.client;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.rinit.debugger.server.client.interfaces.ILibraryServiceClient;
import com.rinit.debugger.server.client.utils.BytesClassLoader;
import com.rinit.debugger.server.controller.urls.FileDriverControllerUrls;
import com.rinit.debugger.server.exception.ServiceException;
import com.rinit.debugger.server.file.library.LibraryDriver;
import com.rinit.debugger.server.services.interfaces.IFileDriverService;
import com.rinit.debugger.server.utils.ObjectsBytesUtils;

public class FileDriverServiceClient implements IFileDriverService {

	private IClient client;
	private String serviceHost;
	private RestTemplate template = new RestTemplate();
	
	public FileDriverServiceClient(IClient client, String serviceHost) {
		this.serviceHost = serviceHost;
		this.client = client;
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

	@Override
	@SuppressWarnings("unchecked")
	public Map<String, LibraryDriver> getFileDrivers() throws ServiceException {
		ILibraryServiceClient libraryServiceClient = this.client.getLibraryServiceClient();
		Map<String, LibraryDriver> driversLibraries = (Map<String, LibraryDriver>) ObjectsBytesUtils.objectFromBytes(this.template.getForObject(this.serviceHost + FileDriverControllerUrls.GET_FILE_DRIVERS, byte[].class));
		for(String libName : driversLibraries.keySet()) {
			driversLibraries.put(libName, libraryServiceClient.convertRemoteLibraryToLocal(driversLibraries.get(libName)));
		}
		return driversLibraries;
	}


}

