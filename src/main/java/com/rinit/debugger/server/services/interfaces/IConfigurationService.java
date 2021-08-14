package com.rinit.debugger.server.services.interfaces;

import com.rinit.debugger.server.exception.ServiceException;

public interface IConfigurationService {
	
	public void createOrCheckDirectoryStructure() throws ServiceException;
	public void clearRunDirectory() throws ServiceException;
}
