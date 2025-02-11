package com.rinit.debugger.server.controller;

import java.io.IOException;
import java.io.InputStream;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rinit.debugger.server.controller.urls.PhysicalFileControllerUrls;
import com.rinit.debugger.server.exception.ControllerException;
import com.rinit.debugger.server.exception.ServiceException;
import com.rinit.debugger.server.file.pfille.PhysicalFileDriver;
import com.rinit.debugger.server.services.interfaces.IPhysicalFileService;

@RestController
public class PhysicalFileController {
	
	@Autowired
	private IPhysicalFileService physicalFileService;
	
	
	
	private static final Logger logger = LoggerFactory.getLogger(PhysicalFileController.class);

	
	@PostMapping(PhysicalFileControllerUrls.LOAD_FILE_URL)
	ResponseEntity<PhysicalFileDriver> loadPhysicalFile(@RequestPart(value = "file", required = false) MultipartFile file, @RequestParam(value = "name", required=false) String name) throws ControllerException {
		PhysicalFileDriver physicalFile = PhysicalFileDriver.builder().name(name).build();
		PhysicalFileDriver savedPhysicalFile;
		try {
			savedPhysicalFile = physicalFileService.uploadFile(physicalFile, file);
		} catch (ServiceException e) {
			throw new ControllerException(e.getMessage());
		}
		return new ResponseEntity<> (savedPhysicalFile, HttpStatus.OK);
	}
	
	
	@GetMapping(PhysicalFileControllerUrls.DOWNLOAD_FILE_BY_PHYSICAL_PATH)
	public ResponseEntity<byte[]> downloadPfileByPhysicalPath(@RequestParam(name="ppath") String ppath) throws ServiceException, IOException {
		InputStream resource = this.physicalFileService.getPfileByPhysicalPath(ppath);
		byte[] bytes = resource.readAllBytes();
		
		return ResponseEntity.ok()
	            .contentLength(bytes.length)	
	            .contentType(MediaType.APPLICATION_OCTET_STREAM)
	            .body(bytes);
	} 	
	
}
