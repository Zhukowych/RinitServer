package com.rinit.debugger.server.services.bin;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rinit.debugger.server.client.ClientFactory;
import com.rinit.debugger.server.dev.core.DevBins;
import com.rinit.debugger.server.dev.core.DevConfiguration;
import com.rinit.debugger.server.dto.FileDTO;
import com.rinit.debugger.server.exception.ServiceException;
import com.rinit.debugger.server.file.ProcessDriver;
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
	
	@Autowired
	private ClientFactory clientFactory;
	
	@Autowired
	private DevConfiguration devConfiguration;
	
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
	public FileDTO runBinWithName(String name, String[] params) throws ServiceException {
		Class<? extends AbstractBin> binClass = null;
		if (this.devConfiguration.isDev())
			binClass = this.getRunBinClassDev(name);
		if (binClass == null)
			binClass = this.getRunBinClassDefault(name);
		
		BinRunner runner = new BinRunner(binClass);
		runner.setClient(clientFactory.newInstance());
		runner.setParams(params);
		Thread procces = new Thread(runner);
		procces.start();
		
		ProcessDriver proccesFile = new ProcessDriver();
		proccesFile.setPid(procces.getId());
		proccesFile.setMessage(runner.getStartUpMessage());
		FileDTO proccesDto = proccesFile.toDTO();

		return fileService.createFile(proccesDto);
	
	}

	@Override
	public void killProcess(String kill) {
	}

	@Override
	public List<String> getAwailableBinsNames() {
		List<String> binNames = new ArrayList<String>();
		if (this.devConfiguration.isDev())
			binNames.addAll(this.getAwailableBinsNamesDev());
		binNames.addAll(this.getAwailableBinsNamesDefault());
		return binNames;
	}
	
	private Class<? extends AbstractBin> getRunBinClassDev(String name) {
		DevBins devBins = new DevBins();
		return devBins.getBinClassByName(name);
	}
	
	@SuppressWarnings("unchecked")
	private Class<AbstractBin> getRunBinClassDefault(String name) {
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
		return binClass;
	}
	
	private List<String> getAwailableBinsNamesDev() {
		DevBins devBins = new DevBins();
		return devBins.getBinsNames();
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
