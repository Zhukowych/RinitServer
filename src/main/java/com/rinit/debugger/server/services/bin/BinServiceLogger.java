package com.rinit.debugger.server.services.bin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.rinit.debugger.server.core.Env;
import com.rinit.debugger.server.dto.FileDTO;
import com.rinit.debugger.server.exception.ServiceException;
import com.rinit.debugger.server.file.bin.BinLoadReportSerializer;
import com.rinit.debugger.server.services.interfaces.IFileService;
import com.rinit.debugger.server.services.library.LibraryLogger;
import com.rinit.debugger.server.utils.ExceptionUtils;

@Component
@DependsOn("Env")
public class BinServiceLogger {
	
	private IFileService fileService;
	
	private FileDTO statusFile = FileDTO.builder().name("status").extention("status").path("/run/services/bin/").build();
	
	private static final Logger logger = LoggerFactory.getLogger(LibraryLogger.class);
	
	
	public BinServiceLogger() {
		this.fileService = Env.getFileService();
		this.createStatusFile();
	}
	
	public void logStatus(BinLoadReportSerializer loadReport) {
		this.statusFile.cwrite(loadReport.toString());
		try {
			this.statusFile = this.fileService.saveFile(statusFile);
		} catch (ServiceException e) {
			logger.error("can't save status file in bin service", ExceptionUtils.stackTraceToString(e));
		}
	}
	
	private void createStatusFile() {
		try {
			this.statusFile = this.fileService.createFile(statusFile);
		} catch (ServiceException e) {
			logger.error("can't create status file in bin service", ExceptionUtils.stackTraceToString(e));
		}		
	}
	
}
