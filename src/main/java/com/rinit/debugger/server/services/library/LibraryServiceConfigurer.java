package com.rinit.debugger.server.services.library;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rinit.debugger.server.services.interfaces.ILibraryService;

@Component
public class LibraryServiceConfigurer {
	
	@Autowired
	private ILibraryService libraryService;
	
	public void configure() {
		libraryService.loadLibraries();
	}
}
