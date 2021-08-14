package com.rinit.debugger.server.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rinit.debugger.server.exception.ServiceException;
import com.rinit.debugger.server.services.interfaces.IConfigurationService;

@Component
public class StartUpConfigurer {

	@Autowired
	private IConfigurationService configurationContiller;
	
	@PostConstruct
	public void runAfterObjectCreated() {
		try {
			configurationContiller.createOrCheckDirectoryStructure();
			configurationContiller.clearRunDirectory();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		
	}
}
