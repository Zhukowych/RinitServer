package com.rinit.debugger.server.services.library;

import org.springframework.stereotype.Component;

import com.rinit.debugger.server.dto.FileDTO;
import com.rinit.debugger.server.utils.RLogger;

@Component
public class LibraryLogger extends RLogger{

	private static FileDTO logFile = FileDTO.builder().name("log").extention("log").path("/run/services/library/").build();
	
	public LibraryLogger() {
		super(logFile);
	}

}
