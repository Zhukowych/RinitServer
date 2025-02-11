package com.rinit.debugger.server.services;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.rinit.debugger.server.dto.FileDTO;
import com.rinit.debugger.server.exception.ServiceException;
import com.rinit.debugger.server.file.pfille.PhysicalFileDriver;
import com.rinit.debugger.server.services.file.FileService;
import com.rinit.debugger.server.services.interfaces.IFileService;
import com.rinit.debugger.server.services.interfaces.IPhysicalFileService;
import com.rinit.debugger.server.utils.ExceptionUtils;

@Service
public class PhysicalFileService implements IPhysicalFileService {
	
	@Value("${app.upload.dir}")
	private String uploadDir;
	
	@Autowired
	private IFileService fileService;

	private static final Logger logger = LoggerFactory.getLogger(FileService.class);

	
	public PhysicalFileDriver uploadFile(PhysicalFileDriver physicalFile, MultipartFile file) throws ServiceException {
    	Path copyLocation = Paths
	        .get(uploadDir + File.separator + StringUtils.cleanPath(file.getOriginalFilename()));
    	physicalFile.setFilePath(copyLocation.toString());
    	FileDTO fileDTO;
    	try {
    		fileDTO = fileService.createFile(physicalFile.toDTO());
    	} catch(ServiceException ex) {
    		this.fileService.deleteFile(fileService.getFileByPathAndName(PhysicalFileDriver.FILE_SYSTEM_DIR, physicalFile.getName()).get(0));
    		fileDTO = fileService.createFile(physicalFile.toDTO());
    	}
    
	    try {
			Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			logger.error(ExceptionUtils.stackTraceToString(e));
			throw new ServiceException(e.getMessage(), e);
		}
	    

	    return physicalFile;
	}

	@Override
	public InputStream getPfileByPhysicalPath(String ppath) throws ServiceException {
		File file = new File(ppath);
		InputStream resource = null;
	    try {
			 resource = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw new ServiceException(String.format("file with path %s not founded", ppath));
		}

		return resource;
	}

}
