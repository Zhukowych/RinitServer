package com.rinit.debugger.server.services.library;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rinit.debugger.server.dto.FileDTO;
import com.rinit.debugger.server.file.driver.LibraryDriver;
import com.rinit.debugger.server.services.interfaces.IFileService;
import com.rinit.debugger.server.services.interfaces.ILibraryService;
import com.rinit.debugger.server.utils.FileDTOUtils;
import com.rinit.debugger.server.utils.RLogger;


@Service
public class LibraryService implements ILibraryService {

	@Autowired
	private IFileService fileService;

	@Autowired
	private LibraryLogger logger;
	
	private Map<String, List<LibraryDriver>> librariesByDirectories = new HashMap<String, List<LibraryDriver>>();
	
	@Override
	public void loadLibraries() {
		List<String> libratiesDirs = this.getLibrariesDirectories();
		this.addLibrariesToMap(libratiesDirs);
		logger.info("test");
	}
	
	private List<String> getLibrariesDirectories() {
		List<String> paths  = FileDTOUtils.getChildrenPathsList(fileService.getFilesByPathAndExtention("/lib/", "directory"));
		int i = 0;
		while( i < paths.size()) {
			paths.addAll(FileDTOUtils.getChildrenPathsList(fileService.getFilesByPathAndExtention(paths.get(i), "directory")));
			i++;
		}
		return paths;
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
			dirsLibraryDrivers.add(library);
		}
		librariesByDirectories.put(dir, dirsLibraryDrivers);
	}
	
	
	

}
