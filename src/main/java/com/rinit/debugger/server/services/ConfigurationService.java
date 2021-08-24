

package com.rinit.debugger.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rinit.debugger.server.services.file.FileServiceConfigurer;
import com.rinit.debugger.server.services.interfaces.IConfigurationService;
import com.rinit.debugger.server.services.library.LibraryServiceConfigurer;

@Service
public class ConfigurationService implements IConfigurationService {
	
	@Autowired
	private LibraryServiceConfigurer libraryServiceConfigurer;
	
	@Override
	public void configure() {
		libraryServiceConfigurer.configure();
	}
	
}
