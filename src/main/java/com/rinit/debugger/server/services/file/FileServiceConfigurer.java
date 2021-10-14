
package com.rinit.debugger.server.services.file;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rinit.debugger.server.dto.FileDTO;
import com.rinit.debugger.server.exception.ServiceException;
import com.rinit.debugger.server.services.interfaces.IFileService;

public class FileServiceConfigurer {
	
	private IFileService fileService;
	
	// HOME
	private FileDTO homeDirectory = FileDTO.builder().name("home").extention("directory").path("/").position(0).content("").build();
	
	// LIB
	private FileDTO libDirectory = FileDTO.builder().name("lib").extention("directory").path("/").position(1).content("").build();
	private FileDTO libExtDirectory = FileDTO.builder().name("ext").extention("directory").path("/lib/").position(0).content("").build();
	private FileDTO libBinDirectory = FileDTO.builder().name("bin").extention("directory").path("/lib/").position(0).content("").build();
	
	// BIN
	private FileDTO binDirectory = FileDTO.builder().name("bin").extention("directory").path("/").position(1).content("").build();
	
	// RUN
	private FileDTO runDirectory = FileDTO.builder().name("run").extention("directory").path("/").position(0).content("").build();
	private FileDTO servicesDirectory = FileDTO.builder().name("services").extention("directory").path("/run/").position(0).content("").build();

	private FileDTO procDirectory = FileDTO.builder().name("proc").extention("directory").path("/run/").position(0).content("").build();

	// ENVIREMENT SERVICES DIRECTORIES
	private FileDTO libraryServiceDirectory = FileDTO.builder().name("library").extention("directory").path("/run/services/").position(0).content("").build();
	
	
	// PHYSICAL FILE
	private FileDTO physicalFilesDirectory = FileDTO.builder().name("physical_files").extention("directory").path("/").position(0).content("").build();
	
	// VAR
	private FileDTO varDirectory = FileDTO.builder().name("var").extention("directory").path("/").position(0).content("").build();
	
	
	private static final Logger logger = LoggerFactory.getLogger(FileServiceConfigurer.class);
	
	public FileServiceConfigurer(IFileService fileService) {
		this.fileService = fileService;
	}
	
	public void configure() {
		try {
			this.clearRunDirectory();
			this.createOrCheckDirectoryStructure();
		}catch(ServiceException e) {
			e.printStackTrace();
		}
	}
	
	private void createOrCheckDirectoryStructure() throws ServiceException {	
		try {
			fileService.createOrCheckFile(homeDirectory);
			fileService.createOrCheckFile(libDirectory);
			fileService.createOrCheckFile(libExtDirectory);
			fileService.createOrCheckFile(libBinDirectory);
			fileService.createOrCheckFile(binDirectory);
			fileService.createOrCheckFile(physicalFilesDirectory);
			
			fileService.createOrCheckFile(runDirectory);
			fileService.createOrCheckFile(servicesDirectory);
			fileService.createOrCheckFile(procDirectory);
			
			fileService.createOrCheckFile(varDirectory);			
			this.createServicesDirectories();
		} catch (ServiceException e) {
			logger.error("Can't check or create files");
			throw new ServiceException("Can't check or create files", e);
		}
	}

	private void clearRunDirectory() throws ServiceException {
		try {
			fileService.deleteAllChildrenOfPath("/run/");
		} catch (ServiceException e) {
			logger.error("Can't delete contents of run/ directrory");
			throw new ServiceException("Can't delete contents of run/ directrory", e);
		}
	}
	
	private void createServicesDirectories() throws ServiceException  {
		fileService.createOrCheckFile(libraryServiceDirectory);
	}
	
}
