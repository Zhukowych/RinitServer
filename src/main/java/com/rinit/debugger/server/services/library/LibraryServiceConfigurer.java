package com.rinit.debugger.server.services.library;

import com.rinit.debugger.server.services.interfaces.ILibraryService;

public class LibraryServiceConfigurer {
	
	private ILibraryService libraryService;
	
	public LibraryServiceConfigurer(ILibraryService libraryService) {
		this.libraryService = libraryService;
	}
	
	public void configure() {
		libraryService.autodiscover();
	}
}
