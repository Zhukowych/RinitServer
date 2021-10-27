package com.rinit.debugger.server.dev.core;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import com.rinit.debugger.server.file.library.LibraryDriver;
import com.rinit.debugger.server.file.pfille.PhysicalFileDriver;


public class DevLibs {
	
	private Map<String, List<LibraryDriver>> librariesByPathes = new HashMap<String, List<LibraryDriver>>();
	
	public DevLibs() {
		this.createDevLibraries();
	}
	
	public LibraryDriver getLibraryDriverByPathName(String path, String name) {
		List<LibraryDriver> libraries = this.librariesByPathes.get(path);
		if (libraries == null) 
			return null;
		for (LibraryDriver library : libraries) {
			if (library.getName().equals(name)) 
				return library;
		}
		return null;
	}
	
	public List<String> getDevLibrariesPathes() {
		List<String> pathes = new ArrayList<String>();
		pathes.addAll(this.librariesByPathes.keySet());
		return pathes;
	}
	
	public List<String> getDevLibrariesNamesByPathes(String path) {
		List<LibraryDriver> libraries = this.librariesByPathes.get(path);
		List<String> names = new ArrayList<String>();
		if (libraries == null) 
			return null;
		for (LibraryDriver library : this.librariesByPathes.get(path)) {
			names.add(library.getName());
		}
		return names;
	}
	
	private void createDevLibraries() {
		
		Reflections reflections = new Reflections("com.rinit.debugger.server.dev.usr.lib", new SubTypesScanner(false));
		for(Class<? extends DevLib> devLibClass : reflections.getSubTypesOf(DevLib.class)) {
			LibraryDriver library = this.createLibraryDriver(this.createDevLibInstance(devLibClass));
			List<LibraryDriver> libraries = this.librariesByPathes.get(library.getPath());
			if (libraries == null) {
				libraries = new ArrayList<LibraryDriver>();
				libraries.add(library);
				this.librariesByPathes.put(library.getPath(), libraries);
			} else {
				libraries.add(library);
			}
		}
	}
	
	private DevLib createDevLibInstance(Class<? extends DevLib> devLibClass) {
		try {
			return devLibClass.getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private LibraryDriver createLibraryDriver(DevLib devLib) {
		PhysicalFileDriver pfile = new PhysicalFileDriver();
		LibraryDriver library = new LibraryDriver();
		library.setPhysicalFile(pfile);
		library.setName(devLib.getName());
		library.setPath(devLib.getPath());
		library.setLoadedClasses(devLib.getLoadedClasses());
		return library;
	}
	
	
	
}
