package com.rinit.debugger.server.services.library;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rinit.debugger.server.core.Extentions;
import com.rinit.debugger.server.dto.FileDTO;
import com.rinit.debugger.server.file.library.LibraryDriver;
import com.rinit.debugger.server.file.library.LibraryLoadReportSerializer;
import com.rinit.debugger.server.file.library.LibraryNotFoundException;
import com.rinit.debugger.server.services.interfaces.IFileService;
import com.rinit.debugger.server.services.interfaces.ILibraryService;


@Service
public class LibraryService implements ILibraryService {

	@Autowired
	private IFileService fileService;
	
	@Autowired
	private LibraryLogger logger;
	
	private Map<String, List<LibraryDriver>> librariesByDirectories = new HashMap<String, List<LibraryDriver>>();
	private LibraryLoadReportSerializer serviceStatusReport = new LibraryLoadReportSerializer();

	@PostConstruct
	private void configure() {
		LibraryServiceConfigurer libraryConfigurer = new LibraryServiceConfigurer(this);
		libraryConfigurer.configure();
	}
	
	@Override
	public LibraryDriver getLibraryByPathName(String path, String name) throws LibraryNotFoundException {
		List<LibraryDriver> librariesInDir = librariesByDirectories.get(path);
		if (librariesInDir == null) 
			throw new LibraryNotFoundException("There is no such library");
		
		for (LibraryDriver library : librariesInDir) {
			if (library.getName().equals(name)) {
				return library;
			}
		} 
		throw new LibraryNotFoundException("There is no such library");
	}
	
	@Override
	public void loadLibraries() {
		List<String> libratiesDirs = fileService.getAllChildrenDirs("/lib/", Extentions.DIRECTORY);
		this.addLibrariesToMap(libratiesDirs);
		this.logResult();
	}
	
	private void addLibrariesToMap(List<String> libraryDirs){
		for(String dir : libraryDirs) {
			this.addDirsLibrariesToMap(dir, fileService.getFilesByPathAndExtention(dir, LibraryDriver.EXTENTION));
		}
	}
	
	private void addDirsLibrariesToMap(String dir, List<FileDTO> librariesDTOs) {
		List<LibraryDriver> dirsLibraryDrivers = new ArrayList<LibraryDriver>(librariesDTOs.size());
		for (FileDTO dto : librariesDTOs) {
			LibraryDriver library = new LibraryDriver();
			library.fromDTO(dto);
			library.loadClasses();
			dirsLibraryDrivers.add(library);
			this.serviceStatusReport.addLibraryReport(library.getLoadReport());
		}
		librariesByDirectories.put(dir, dirsLibraryDrivers);
	}
	
	private void logResult() {
		logger.info("libraries loaded");
		logger.logStatus(serviceStatusReport);
	}
	
}
