package com.rinit.debugger.server.controller;

import java.util.List;

import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.rinit.debugger.server.file.library.LibraryDriver;
import com.rinit.debugger.server.file.library.LibraryNotFoundException;
import com.rinit.debugger.server.services.library.LibraryService;

@RestController
@Transactional
public class LibraryController {

	@Autowired
	private LibraryService libraryService;
	
	public final static String GET_LIBRARY_BY_PATH_NAME = "library/";
	
	@GetMapping(LibraryController.GET_LIBRARY_BY_PATH_NAME)
	@ResponseBody
	public LibraryDriver getLibraryByPathName(String path, String name) throws LibraryNotFoundException {
		return null;	
	}
	
	public List<String> getLocatedPathes() {
		return null;
	}
	
	public List<String> getLibrariesNamesByPath(String path) throws SerialException {
		return null;
		
	}

	
}
