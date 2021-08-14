package com.rinit.debugger.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rinit.debugger.server.dto.FileDTO;
import com.rinit.debugger.server.exception.ServiceException;
import com.rinit.debugger.server.services.interfaces.IFileService;

@RestController
@Transactional
public class ConfigurationController {
	
	@Autowired
	private IFileService fileService;
	
	public final static String INIT_CONFIGURE = "/init_config/";
	
	@PostMapping(INIT_CONFIGURE)
	ResponseEntity<String> initConfigure(){
		FileDTO homeDirectory = FileDTO.builder().name("home").extention("directory").path("/").position(0).content("").build();
		FileDTO libDirectory = FileDTO.builder().name("lib").extention("directory").path("/").position(1).content("").build();
		FileDTO libExtDirectory = FileDTO.builder().name("ext").extention("directory").path("/lib/").position(0).content("").build();
		FileDTO physicalFilesDirectory = FileDTO.builder().name("physical_files").extention("directory").path("/").position(2).content("").build();
		

		try {
			fileService.createFile(homeDirectory);
			fileService.createFile(libDirectory);
			fileService.createFile(libExtDirectory);
			fileService.createFile(physicalFilesDirectory);
			return new ResponseEntity<>("success", HttpStatus.OK);
		} catch (ServiceException e) {
			return new ResponseEntity<>("fail:" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
