package com.rinit.debugger.server.services.bin;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rinit.debugger.server.dto.FileDTO;
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
	public List<String> getAwailableBinsNames() {
		List<String> binNames = new ArrayList<String>();
		binNames.addAll(this.getAwailableBinsNamesDefault());
		return binNames;
	}
	
	@Override
	public Map<String, Class<?>> getBins() {
		Map<String, Class<?>> bins = new HashMap<String, Class<?>>();
		for (String binName : this.getAwailableBinsNames()) {
			bins.put(binName, this.getBinClass(binName));
		}
		return bins;
	}
	
	private Class<?> getBinClass(String name) {
		BinLoadReportDeserializer deserializer = this.getLoadedBins();
		BinLoadReport loadedBin = deserializer.getLoadedBinByName(name);
		LibraryDriver library = null;
		try {
			library = this.libraryService.getLibraryByPathName(loadedBin.libraryPath, loadedBin.libraryName);
		} catch (LibraryNotFoundException e) {
			e.printStackTrace();
		}
		Class<?> binClass = null;
		try {
			binClass = library.getClassWithName(name);
		} catch (LibraryClassNotFoundException e) {
			e.printStackTrace();
		}
		return binClass;
	}
	
	private List<String> getAwailableBinsNamesDefault() {
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
