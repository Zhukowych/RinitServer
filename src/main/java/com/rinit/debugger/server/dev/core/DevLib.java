package com.rinit.debugger.server.dev.core;

import java.util.Map;

public interface DevLib {
	public String getPath();
	public String getName();
	public Map<String, Class<?>> getLoadedClasses();
}
