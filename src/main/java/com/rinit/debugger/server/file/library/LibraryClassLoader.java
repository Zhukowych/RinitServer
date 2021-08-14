package com.rinit.debugger.server.file.library;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rinit.debugger.server.controller.FilesController;
import com.rinit.debugger.server.file.driver.PhysicalFileDriver;

public class LibraryClassLoader {
	
	private static final Logger logger = LoggerFactory.getLogger(LibraryClassLoader.class);
	
	private List<ClassToLoadInfo> classesToLoad;
	private PhysicalFileDriver physicalFile;
	
	private Map<String, Class> loadedClassed = new HashMap<String, Class>();
	private List<String> errors = new ArrayList<String>();
	
	public void setClassesToLoad(List<ClassToLoadInfo> classesToLoad) {
		this.classesToLoad = classesToLoad;
	}

	public void setPhysicalFile(PhysicalFileDriver physicalFile) {
		this.physicalFile = physicalFile;
	}

	public void loadClasses() {
		File jarFile = new File(this.physicalFile.getFilePath());
		URLClassLoader child = null;
		try {
			child = new URLClassLoader(
			        new URL[] {jarFile.toURI().toURL()},
			        this.getClass().getClassLoader()
			);
		} catch (MalformedURLException e1) {
			logger.error(String.format("There is no physical file with path %s", this.physicalFile.getFilePath()));
		}
		for (ClassToLoadInfo loadInfo : this.classesToLoad) {
			try {
				this.loadedClassed.put(
						loadInfo.getName(), 
						this.getClassFromClassLoader(loadInfo, child)
					);
			} catch (ClassNotFoundException e) {
				logger.warn(String.format("Can't load class %s from jar file %s", loadInfo.getPath(), this.physicalFile.getFilePath()));
			}
		}
	}
	
	public Map<String, Class> getLoadedClasses(){
		return this.loadedClassed;
	}
	
	private Class getClassFromClassLoader(ClassToLoadInfo classToLoad, ClassLoader classLoader) throws ClassNotFoundException {
		return Class.forName(classToLoad.getPath(), true, classLoader);
	}
}
