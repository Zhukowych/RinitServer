package com.rinit.debugger.server.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.rinit.debugger.server.controller.urls.FileDriverControllerUrls;
import com.rinit.debugger.server.exception.ServiceException;
import com.rinit.debugger.server.file.library.LibraryClassNotFoundException;
import com.rinit.debugger.server.file.library.LibraryDriver;
import com.rinit.debugger.server.services.interfaces.IFileDriverService;
import com.rinit.debugger.server.utils.ObjectsBytesUtils;

@RestController
@Transactional
public class FileDriverController {
	
	@Autowired
	private IFileDriverService fileDriverService;

	@GetMapping(FileDriverControllerUrls.GET_AVAILABLE_FILE_DRIVERS)
	@ResponseBody
	public List<String> getAvailableFileDrivers() throws ServiceException {
		return this.fileDriverService.getAvailableFileDrivers();
	}

	@GetMapping(FileDriverControllerUrls.GET_DRIVER_LIBRARY_BY_MAME)
	@ResponseBody
	public LibraryDriver getDriverLibraryByName(@RequestParam("name") String name) throws ServiceException {	
		return this.fileDriverService.getDriverLibraryByName(name);
	}
	
	@GetMapping(FileDriverControllerUrls.GET_FILE_DRIVERS)
	@ResponseBody
	public byte[] getFileDrivers() throws ServiceException, LibraryClassNotFoundException, IOException {	
		return ObjectsBytesUtils.objectToBytes(this.fileDriverService.getFileDrivers());
	}
	
}
