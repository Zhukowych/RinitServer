package com.rinit.debugger.server.services.library;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rinit.debugger.server.core.Extentions;
import com.rinit.debugger.server.dto.FileDTO;
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
	
	private LibraryLoadReportSerializer serviceStatusReport = new LibraryLoadReportSerializer();

	@PostConstruct
	private void configure() {
		LibraryServiceConfigurer libraryConfigurer = new LibraryServiceConfigurer(this);
		libraryConfigurer.configure();
	}
	
	@Override
	public LibraryDriver getLibraryByPathName(String path, String name) throws LibraryNotFoundException {
		FileDTO statusFile = this.fileService.getFileByPathAndName("/run/services/library/", "status").get(0);
		LibraryLoadReportDeserializer libraryServieStatus = new LibraryLoadReportDeserializer(statusFile.getContent());
		if (!libraryServieStatus.isExistLibrary(path, name)) {
			throw new LibraryNotFoundException(String.format("Library in %s and name %s not founded", path, name));
		} else {
			return this.createLibraryPathFromPathName(path, name);
		}
	}

	@Override
	public List<String> getLocatedPathes() {
		FileDTO statusFile = this.fileService.getFileByPathAndName("/run/services/library/", "status").get(0);
		LibraryLoadReportDeserializer libraryServieStatus = new LibraryLoadReportDeserializer(statusFile.getContent());
		return libraryServieStatus.getLocatedPathes();
	}

	@Override
	public List<String> getLibrariesNamesByPath(String path) throws SerialException {
		FileDTO statusFile = this.fileService.getFileByPathAndName("/run/services/library/", "status").get(0);
		LibraryLoadReportDeserializer libraryServieStatus = new LibraryLoadReportDeserializer(statusFile.getContent());
		List<String> libraryNames = libraryServieStatus.getLibrariesNamesByPath(path);
		if (libraryNames == null) {
			throw new SerialException(String.format("libraries names by path %s not found", path));
		}
		return libraryNames;
	}
	
	@Override
	public void checkLibraries() {
		List<String> libratiesDirs = fileService.getAllChildrenDirs("/lib/", Extentions.DIRECTORY);
		this.checkLibrariesByDir(libratiesDirs);
		this.logResult();
	}
	
	private LibraryDriver createLibraryPathFromPathName(String path, String name) {
		FileDTO libraryFile = this.fileService.getFileByPathAndName(path, name).get(0);
		LibraryDriver library = new LibraryDriver();
		library.fromDTO(libraryFile);
		library.loadClasses();
		return library;
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
