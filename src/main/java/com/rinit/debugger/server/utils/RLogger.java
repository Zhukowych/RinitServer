package com.rinit.debugger.server.utils;

import com.rinit.debugger.server.core.Env;
import com.rinit.debugger.server.dto.FileDTO;
import com.rinit.debugger.server.exception.ServiceException;
import com.rinit.debugger.server.services.interfaces.IFileService;

public class RLogger {
	
	protected IFileService fileService;
	
	private FileDTO logFile;
	
	public RLogger(FileDTO logFile) {
		fileService = Env.getFileService();
		try {
			this.logFile = fileService.createFile(logFile);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
	
	public void info(String...messages) {
		this.logFile.write("[INFO]");
		for (String message : messages) {
			this.logFile.write(message);
		}
		try {
			this.fileService.saveFile(logFile);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

}
