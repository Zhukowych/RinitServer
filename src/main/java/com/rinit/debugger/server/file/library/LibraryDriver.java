package com.rinit.debugger.server.file.library;

import java.io.Serializable;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rinit.debugger.server.dto.FileDTO;
import com.rinit.debugger.server.file.AbstractDriver;
import com.rinit.debugger.server.file.IFileDriver;
import com.rinit.debugger.server.file.pfille.PhysicalFileDriver;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LibraryDriver extends AbstractDriver implements IFileDriver, Serializable {

	public final static String EXTENTION = "lib";

	private String name;
	private String path;
	private PhysicalFileDriver physicalFile;
	
	private List<ClassToLoadInfo> classesToLoad = new ArrayList<ClassToLoadInfo>();
	private Map<String, Class<?>> loadedClasses = new HashMap<String, Class<?>>();
	private LibraryLoadReport loadReport;
	
	@Override
	public void fromDTO(FileDTO dto) {
		 this.name = dto.getName();
		 this.path = dto.getPath();
		 LibraryImporter parser = new LibraryImporter();
		 parser.parse(dto.getContent());
		 this.physicalFile = parser.getPhysicalFile();
		 this.classesToLoad = parser.getClassesToLoad();
	}
	
	@Override
	public FileDTO toDTO() {
		return FileDTO.builder()
					  .name(name)
					  .extention(EXTENTION)
					  .position(0)
					  .content(this.getContent())
					  .path(this.path)
					  .build();
	}
	
	@Override
	public String getContent() {
		LibraryExporter exporter = new LibraryExporter();
		exporter.setClassesToLoad(classesToLoad);
		exporter.setPhysicalFile(physicalFile);
		return exporter.export();
	}
	
	public void addClassToLoad(ClassToLoadInfo classToLoadInfo) {
		this.classesToLoad.add(classToLoadInfo);
	}
	
	public void loadClasses() {
		LibraryClassLoader classLoader = new LibraryClassLoader();
		classLoader.setLibrary(this);
		classLoader.setClassesToLoad(classesToLoad);
		classLoader.loadClasses();
		this.loadedClasses = classLoader.getLoadedClasses();
		this.loadReport = classLoader.getLoadReport();
	}
	
	public LibraryLoadReport getLoadReport() {
		return this.loadReport;
	}
	
	public void addClass(String name, Class<?> clazz) {
		this.loadedClasses.put(name, clazz);
	}
	
	public Class<?> getClassWithName(String name) throws LibraryClassNotFoundException{
		Class<?> libraryClass = this.loadedClasses.get(name);
		if (libraryClass != null) {
			return libraryClass;
		} else {
			throw new LibraryClassNotFoundException(String.format("There is no class with name %s in library", name));
		}
	}

	@Override
	protected void buildFromDTO() {
		 this.name = dto.getName();
		 this.path = dto.getPath();
		 LibraryImporter parser = new LibraryImporter();
		 parser.parse(this.getContent());
		 this.physicalFile = parser.getPhysicalFile();
		 this.classesToLoad = parser.getClassesToLoad();
	}

	@Override
	public String buildContent() {
		return this.getContent();
	}
	
}
