package com.rinit.debugger.server.file.library;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rinit.debugger.server.dto.FileDTO;
import com.rinit.debugger.server.entity.IFileDriver;
import com.rinit.debugger.server.file.driver.PhysicalFileDriver;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LibraryDriver implements IFileDriver{

	public final static String EXTENTION = "lib";

	private String name;
	private PhysicalFileDriver physicalFile;
	
	private List<ClassToLoadInfo> classesToLoad = new ArrayList<ClassToLoadInfo>();
	private Map<String, Class> loadedClasses = new HashMap<String, Class>();
	private LibraryLoadReport loadReport;
	
	@Override
	public void fromDTO(FileDTO dto) {
		 this.name = dto.getName();
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
					  .build();
	}
	
	@Override
	public String getContent() {
		LibraryExporter exporter = new LibraryExporter();
		exporter.setClassesToLoad(classesToLoad);
		exporter.setPhysicalFile(physicalFile);
		return exporter.export();
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
	
	public Class<?> getClassWithName(String name) throws LibraryClassNotFoundException{
		Class<?> libraryClass = this.loadedClasses.get(name);
		if (libraryClass != null) {
			return libraryClass;
		} else {
			throw new LibraryClassNotFoundException(String.format("There is no class with name %s in library", name));
		}
	}

}
