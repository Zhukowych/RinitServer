package com.rinit.debugger.server.services.library;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.rinit.debugger.server.dto.FileDTO;
import com.rinit.debugger.server.exception.ServiceException;
import com.rinit.debugger.server.file.library.LibraryLoadReportSerializer;
import com.rinit.debugger.server.utils.ExceptionUtils;
import com.rinit.debugger.server.utils.RLogger;

@Component
@DependsOn("Env")
public class LibraryLogger extends RLogger{

	private static final Logger logger = LoggerFactory.getLogger(LibraryLogger.class);
	
	private static FileDTO logFile = FileDTO.builder().name("log").extention("log").path("/run/services/library/").build();
	private FileDTO statusFile = FileDTO.builder().name("status").extention("status").path("/run/services/library/").build();
	
	
	public LibraryLogger() {
		super(logFile);
		this.createStatusFile();
	}

	public void logStatus(LibraryLoadReportSerializer serviceStatusReport) {
		this.statusFile.cwrite(serviceStatusReport.toString());
		try {
			this.statusFile = this.fileService.saveFile(statusFile);
		} catch (ServiceException e) {
			logger.error("can't save status file in library service", ExceptionUtils.stackTraceToString(e));
		}
	}
	
	private void createStatusFile() {
		try {
			this.statusFile = this.fileService.createFile(statusFile);
		} catch (ServiceException e) {
			logger.error("can't create status file in library service", ExceptionUtils.stackTraceToString(e));
		}		
	}
		
}
