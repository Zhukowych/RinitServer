package com.rinit.debugger.server.services.library;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rinit.debugger.server.core.Extentions;
import com.rinit.debugger.server.dev.core.DevConfiguration;
import com.rinit.debugger.server.dev.core.DevLibs;
import com.rinit.debugger.server.dto.FileDTO;
import com.rinit.debugger.server.exception.ServiceException;
import com.rinit.debugger.server.file.library.LibraryDriver;
import com.rinit.debugger.server.file.library.LibraryLoadReportDeserializer;
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

	@Autowired 
	private DevConfiguration devConfiguration;
	
	private LibraryLoadReportSerializer serviceStatusReport = new LibraryLoadReportSerializer();

	@PostConstruct
	private void configure() {
		LibraryServiceConfigurer libraryConfigurer = new LibraryServiceConfigurer(this);
		libraryConfigurer.configure();
	}
	
	@Override
	public LibraryDriver getLibraryByPathName(String path, String name) throws LibraryNotFoundException {
		LibraryDriver library = null;
		if (this.devConfiguration.isDev()) 
			library = this.getLibraryByPathNameDev(path, name);
		if (library == null) 
			library = this.getLibraryByPathNameDefault(path, name);
		return library;
	}

	@Override
	public List<String> getLocatedPathes() {
		Set<String> pathesSet = new HashSet<String>();
		List<String> pathes = new ArrayList<String>();
		if (this.devConfiguration.isDev())
			pathesSet.addAll(this.getLocatedPathesDev());
		pathesSet.addAll(this.getLocatedPathesDefault());
		pathes.addAll(pathesSet);
		return pathes;
	}
	
	@Override
	public List<String> getLibrariesNamesByPath(String path) throws ServiceException {
		List<String> names = new ArrayList<String>();
		Set<String> namesSet = new HashSet<String>();
		if (this.devConfiguration.isDev())
			namesSet.addAll(this.getLibrariesNamesByPathDev(path));
		namesSet.addAll(this.getLibrariesNamesByPathDefault(path));
		names.addAll(namesSet);
		return names;
	}
	
	@Override
	public void autodiscover() {
		List<String> libratiesDirs = fileService.getAllChildrenDirs("/lib/", Extentions.DIRECTORY);
		this.checkLibrariesByDir(libratiesDirs);
		this.logResult();
	}

	public List<String> getLibrariesNamesByPathDev(String path) {
		DevLibs libs = new DevLibs();
		return libs.getDevLibrariesNamesByPathes(path);
	}
		
	public List<String> getLibrariesNamesByPathDefault(String path) throws ServiceException {
		FileDTO statusFile = this.fileService.getFileByPathAndName("/run/services/library/", "status").get(0);
		LibraryLoadReportDeserializer libraryServieStatus = new LibraryLoadReportDeserializer(statusFile.getContent());
		List<String> libraryNames = libraryServieStatus.getLibrariesNamesByPath(path);
		if (libraryNames == null) {
			throw new ServiceException(String.format("libraries names by path %s not found", path));
		}
		return libraryNames;
	}
	
	private LibraryDriver getLibraryByPathNameDev(String path, String name) {
		DevLibs libs = new DevLibs();
		return libs.getLibraryDriverByPathName(path, name);
	}
	
	private LibraryDriver getLibraryByPathNameDefault(String path, String name) throws LibraryNotFoundException {
		FileDTO statusFile = this.fileService.getFileByPathAndName("/run/services/library/", "status").get(0);
		LibraryLoadReportDeserializer libraryServieStatus = new LibraryLoadReportDeserializer(statusFile.getContent());
		if (!libraryServieStatus.isExistLibrary(path, name)) {
			throw new LibraryNotFoundException(String.format("Library in %s and name %s not founded", path, name));
		} else {
			return this.createLibraryPathFromPathName(path, name);
		}
	}
	
	private LibraryDriver createLibraryPathFromPathName(String path, String name) {
		FileDTO libraryFile = this.fileService.getFileByPathAndName(path, name).get(0);
		LibraryDriver library = new LibraryDriver();
		library.fromDTO(libraryFile);
		return library;
	}
	
	private List<String> getLocatedPathesDev(){
		DevLibs libs = new DevLibs();
		return libs.getDevLibrariesPathes();
	}
	
	private List<String> getLocatedPathesDefault() {
		FileDTO statusFile = this.fileService.getFileByPathAndName("/run/services/library/", "status").get(0);
		LibraryLoadReportDeserializer libraryServieStatus = new LibraryLoadReportDeserializer(statusFile.getContent());
		return libraryServieStatus.getLocatedPathes();		
	}
	
	private void checkLibrariesByDir(List<String> libraryDirs){
		for(String dir : libraryDirs) {
			this.checkDirsLibraries(dir, fileService.getFilesByPathAndExtention(dir, LibraryDriver.EXTENTION));
		}
	}
	
	private void checkDirsLibraries(String dir, List<FileDTO> librariesDTOs) {
		for (FileDTO dto : librariesDTOs) {
			LibraryDriver library = new LibraryDriver();
			library.fromDTO(dto);
			library.loadClasses();
			this.serviceStatusReport.addLibraryReport(library.getLoadReport());
		}
	}
	
	private void logResult() {
		logger.info("libraries loaded");
		logger.logStatus(serviceStatusReport);
	}
	
}
