package com.rinit.debugger.server.core;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.rinit.debugger.server.services.interfaces.IConfigurationService;

@Component
public class StartUpConfigurer implements CommandLineRunner{

	@Autowired
	private IConfigurationService configurationController;
	
	@Override
	public void run(String... args) throws Exception {
		configurationController.configure();
	}
}
