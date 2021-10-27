package com.rinit.debugger.server.services.bin;

import java.util.List;

import com.rinit.debugger.server.core.Env;
import com.rinit.debugger.server.core.Extentions;
import com.rinit.debugger.server.dto.FileDTO;
import com.rinit.debugger.server.file.bin.BinDriver;
import com.rinit.debugger.server.file.bin.BinLoadReport;
import com.rinit.debugger.server.file.bin.BinLoadReportSerializer;
import com.rinit.debugger.server.file.library.LibraryNotFoundException;
import com.rinit.debugger.server.services.interfaces.IFileService;
import com.rinit.debugger.server.services.interfaces.ILibraryService;

public class BinAutodiscoverer {
	
	private IFileService fileService;

	private ILibraryService libraryService;
	
	private BinServiceLogger logger;
	
	private BinLoadReportSerializer loadReportSerializer = new BinLoadReportSerializer();
	
	public BinAutodiscoverer() {
		this.fileService = Env.getFileService();
		this.libraryService = Env.getLibraryService();
		this.logger = (BinServiceLogger) Env.getBean(BinServiceLogger.class);
	}	
	
	public void autodiscover() {
		List<FileDTO> binFiles = fileService.getFilesByParentPathExtention("/bin/", Extentions.BIN);
		for (FileDTO binFile : binFiles) {
			this.checkBinFile(binFile);
		}
		this.logger.logStatus(loadReportSerializer);
	}
	
	private void checkBinFile(FileDTO binFile) {
		BinLoadReport loadReport = new BinLoadReport();
		BinDriver bin = new BinDriver();
		bin.fromDTO(binFile);
		loadReport.name = bin.getName();
		loadReport.libraryPath = bin.getBinLibraryPath();
		loadReport.libraryName = bin.getBinLibraryName();
		try {
			this.libraryService.getLibraryByPathName(bin.getBinLibraryPath(), bin.getBinLibraryName());
		} catch (LibraryNotFoundException e) {
			loadReport.error = String.format("cannot find  bin class for bin %s", bin.getName());
		}
		this.loadReportSerializer.addLoadReport(loadReport);
	}
	
}
