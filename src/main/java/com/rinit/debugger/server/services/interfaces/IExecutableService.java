package com.rinit.debugger.server.services.interfaces;

import java.util.List;

public interface IExecutableService {
	
	public void loadExecutables();
	public void runExecutableWithName(String name);
	public void killProcess(String kill);
	
}
