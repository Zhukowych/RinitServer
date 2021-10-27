package com.rinit.debugger.server.dev.usr.lib;

import java.util.HashMap;
import java.util.Map;

import com.rinit.debugger.server.dev.core.DevLib;

public class TestLibrary implements DevLib {

	private final static String PATH = "/lib/bin/";
	private final static String NAME = "test_lib_dev";

	@Override
	public String getPath() {
		return TestLibrary.PATH;
	}

	@Override
	public String getName() {
		return TestLibrary.NAME;
	}

	@Override
	public Map<String, Class<?>> getLoadedClasses() {
		Map<String, Class<?>> loadedClasses = new HashMap<String, Class<?>>();
		loadedClasses.put("testClass", this.getClass());
		return loadedClasses;
	}

}
