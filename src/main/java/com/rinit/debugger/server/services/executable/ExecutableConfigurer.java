package com.rinit.debugger.server.services.executable;

import com.rinit.debugger.server.services.interfaces.IExecutableService;

public class ExecutableConfigurer {
	
	private IExecutableService executableService;
	
	public ExecutableConfigurer(IExecutableService executableService) {
		this.executableService = executableService;
	}
	
	public void config() {
		executableService.loadExecutables();
	}
}
