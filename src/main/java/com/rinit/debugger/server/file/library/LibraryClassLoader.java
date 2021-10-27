package com.rinit.debugger.server.file.library;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rinit.debugger.server.file.pfille.PhysicalFileDriver;

public class LibraryClassLoader {
	
	private static final Logger logger = LoggerFactory.getLogger(LibraryClassLoader.class);
	
	private List<ClassToLoadInfo> classesToLoad;
	
	private LibraryDriver library;
	private PhysicalFileDriver physicalFile;
	
	private Map<String, Class<?>> loadedClassed = new HashMap<String, Class<?>>();
	private LibraryLoadReport loadReport = new LibraryLoadReport();
	
	public void setClassesToLoad(List<ClassToLoadInfo> classesToLoad) {
		this.classesToLoad = classesToLoad;
	}

	public void setLibrary(LibraryDriver library) {
		this.library = library;
		this.loadReport.setLibrary(this.library);
		this.physicalFile = this.library.getPhysicalFile();
	}

	public Map<String, Class<?>> getLoadedClasses(){
		return this.loadedClassed;
	}
	
	public LibraryLoadReport getLoadReport() {
		return this.loadReport;
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
			String error = String.format("There is no physical file with path %s", this.physicalFile.getFilePath());
			this.loadReport.addError(error);
			logger.error(error);
		}
		for (ClassToLoadInfo loadInfo : this.classesToLoad) {
			try {
				this.loadedClassed.put(
						loadInfo.getName(), 
						this.getClassFromClassLoader(loadInfo, child)
					);
				this.loadReport.addLoadedClass(loadInfo);
			} catch (ClassNotFoundException e) {
				String error = String.format("Can't load class %s from jar file %s", loadInfo.getPath(), this.physicalFile.getFilePath()); 
				this.loadReport.addError(error);
				logger.error(error);
			}
		}
	}
	
	private Class getClassFromClassLoader(ClassToLoadInfo classToLoad, ClassLoader classLoader) throws ClassNotFoundException {
		return Class.forName(classToLoad.getPath(), true, classLoader);
	}

}
