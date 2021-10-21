package com.rinit.debugger.server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.rinit.debugger.server.controller.urls.LibraryControllerUrls;
import com.rinit.debugger.server.exception.ServiceException;
import com.rinit.debugger.server.file.library.LibraryDriver;
import com.rinit.debugger.server.file.library.LibraryNotFoundException;
import com.rinit.debugger.server.services.library.LibraryService;

@RestController
@Transactional
public class LibraryController {

	@Autowired
	private LibraryService libraryService;
	
	
	@GetMapping(LibraryControllerUrls.GET_LIBRARY_BY_PATH_NAME)
	@ResponseBody
	public LibraryDriver getLibraryByPathName(@RequestParam("path") String path, @RequestParam("name") String name) throws LibraryNotFoundException {
		return this.libraryService.getLibraryByPathName(path, name);	
	}
	
	@GetMapping(LibraryControllerUrls.GET_LIBRARIES_PATHES)
	@ResponseBody
	public List<String> getLocatedPathes() {
		return this.libraryService.getLocatedPathes();
	}
	
	@GetMapping(LibraryControllerUrls.GET_LIBRARY_NAMES_BY_PATH)
	@ResponseBody
	public List<String> getLibrariesNamesByPath(@RequestParam("path") String path) throws ServiceException {
		return this.libraryService.getLibrariesNamesByPath(path);
	}
	
}
