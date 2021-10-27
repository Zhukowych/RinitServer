package com.rinit.debugger.server.services.bin;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rinit.debugger.server.dto.FileDTO;
import com.rinit.debugger.server.file.bin.AbstractBin;
import com.rinit.debugger.server.file.bin.BinLoadReport;
import com.rinit.debugger.server.file.bin.BinLoadReportDeserializer;
import com.rinit.debugger.server.file.library.LibraryClassNotFoundException;
import com.rinit.debugger.server.file.library.LibraryDriver;
import com.rinit.debugger.server.file.library.LibraryNotFoundException;
import com.rinit.debugger.server.services.interfaces.IBinService;
import com.rinit.debugger.server.services.interfaces.IFileService;
import com.rinit.debugger.server.services.interfaces.ILibraryService;

@Service
public class BinService implements IBinService {
	
	@Autowired
	private IFileService fileService;
	
	@Autowired
	private ILibraryService libraryService;
	
	@PostConstruct
	public void configure() {
		this.autodiscoverBins();
	}
	
	@Override
	public void autodiscoverBins() {
		BinAutodiscoverer autodiscoverer = new BinAutodiscoverer();
		autodiscoverer.autodiscover();
	}

	@Override
	public void runBinWithName(String name) {
		BinLoadReportDeserializer deserializer = this.getLoadedBins();
		BinLoadReport loadedBin = deserializer.getLoadedBinByName(name);
		LibraryDriver library = null;
		try {
			library = this.libraryService.getLibraryByPathName(loadedBin.libraryPath, loadedBin.libraryName);
		} catch (LibraryNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Class<AbstractBin> binClass = null;
		try {
			binClass = (Class<AbstractBin>) library.getClassWithName(name);
		} catch (LibraryClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BinRunner runner = new BinRunner(binClass);
		Thread procces = new Thread(runner);
		procces.start();
	}

	@Override
	public void killProcess(String kill) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<String> getAwailableBins() {
		FileDTO statusFile = this.fileService.getFileByPathAndName("/run/services/bin/", "status").get(0);
		BinLoadReportDeserializer deserializer = new BinLoadReportDeserializer(statusFile.getContent());
		return deserializer.getBinNames();
	}
	
	private BinLoadReportDeserializer getLoadedBins() {
		FileDTO statusFile = this.fileService.getFileByPathAndName("/run/services/bin/", "status").get(0);
		BinLoadReportDeserializer deserializer = new BinLoadReportDeserializer(statusFile.getContent());
		return deserializer;
	}

}
