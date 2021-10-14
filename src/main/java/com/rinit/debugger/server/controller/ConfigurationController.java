package com.rinit.debugger.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rinit.debugger.server.file.library.LibraryClassNotFoundException;
import com.rinit.debugger.server.file.library.LibraryNotFoundException;
import com.rinit.debugger.server.services.interfaces.ILibraryService;

@RestController
@Transactional
public class ConfigurationController {
	
	@Autowired
	private ILibraryService libraryService;
	
	
	public final static String INIT_CONFIGURE = "/init_config/";
	
	@GetMapping(INIT_CONFIGURE)
	ResponseEntity<Class> initConfigure() throws LibraryClassNotFoundException, LibraryNotFoundException{
		return new ResponseEntity<>(libraryService.getLibraryByPathName("/lib/ext/", "test_lib").getClassWithName("test"), HttpStatus.OK);
	}
	
}
