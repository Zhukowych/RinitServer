package com.rinit.debugger.server.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.rinit.debugger.server.controller.urls.FileControllerUrls;
import com.rinit.debugger.server.dto.FileDTO;
import com.rinit.debugger.server.exception.ServiceException;
import com.rinit.debugger.server.services.interfaces.IFileService;

@RestController
@Transactional
public class FilesController {
	
	@Autowired
	private IFileService fileService;
	
	private static final Logger logger = LoggerFactory.getLogger(FilesController.class);

	@PostMapping(FileControllerUrls.CREATE_FILE_URL)
	@ResponseBody
	public ResponseEntity<FileDTO> createFile(@RequestBody FileDTO fileDTO) {
		FileDTO savedFile;
		try {
			savedFile = fileService.createFile(fileDTO);
		} catch (ServiceException e) {
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
		}
		return new ResponseEntity<>(savedFile, HttpStatus.OK);
	}
	
	@PostMapping(FileControllerUrls.SAVE_FILE)
	@ResponseBody
	public ResponseEntity<FileDTO> saveFile(@RequestBody FileDTO fileDTO) {
		FileDTO savedFile;
		try {
			savedFile = fileService.saveFile(fileDTO);
		} catch (ServiceException e) {
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
		}
		return new ResponseEntity<>(savedFile, HttpStatus.OK);
	}
	
	@GetMapping(FileControllerUrls.GET_FILES_URL)
	@ResponseBody
	public ResponseEntity<List<FileDTO>> getFilesByPath(@RequestParam("path") String path) {
		return new ResponseEntity<>(this.fileService.getFilesByPath(path), HttpStatus.OK);
	}
	
	@GetMapping(FileControllerUrls.GET_FILES_BY_PATH_NAME)
	@ResponseBody
	public List<FileDTO> getFileByPathAndName(@RequestParam("path") String path, @RequestParam("name") String name){
		return this.fileService.getFileByPathAndName(path, name);
	}

}
