package com.rinit.debugger.server.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rinit.debugger.server.dto.FileDTO;
import com.rinit.debugger.server.exception.ServiceException;
import com.rinit.debugger.server.services.interfaces.IConfigurationService;
import com.rinit.debugger.server.services.interfaces.IFileService;

@Component
public class ConfigurationService implements IConfigurationService {

	@Autowired 
	private IFileService fileService;
	
	private static final Logger logger = LoggerFactory.getLogger(ConfigurationService.class);
	
	@Override
	public void createOrCheckDirectoryStructure() throws ServiceException {
		FileDTO homeDirectory = FileDTO.builder().name("home").extention("directory").path("/").position(0).content("").build();
		FileDTO libDirectory = FileDTO.builder().name("lib").extention("directory").path("/").position(1).content("").build();
		FileDTO libExtDirectory = FileDTO.builder().name("ext").extention("directory").path("/lib/").position(0).content("").build();
		FileDTO physicalFilesDirectory = FileDTO.builder().name("physical_files").extention("directory").path("/").position(0).content("").build();
		FileDTO runDirectory = FileDTO.builder().name("run").extention("directory").path("/").position(0).content("").build();
		FileDTO varDirectory = FileDTO.builder().name("var").extention("directory").path("/").position(0).content("").build();
		
		
		try {
			fileService.createOrCheckFile(homeDirectory);
			fileService.createOrCheckFile(libDirectory);
			fileService.createOrCheckFile(libExtDirectory);
			fileService.createOrCheckFile(physicalFilesDirectory);
			fileService.createOrCheckFile(runDirectory);
			fileService.createOrCheckFile(varDirectory);			
		} catch (ServiceException e) {
			logger.error("Can't check or create files");
			throw new ServiceException("Can't check or create files", e);
		}
	}

	@Override
	public void clearRunDirectory() throws ServiceException {
		try {
			fileService.deleteAllChildrenOfPath("run/");
		} catch (ServiceException e) {
			logger.error("Can't delete contents of run/ directrory");
			throw new ServiceException("Can't delete contents of run/ directrory", e);
		}
	}

}
