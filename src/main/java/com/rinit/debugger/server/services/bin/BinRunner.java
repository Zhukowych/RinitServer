package com.rinit.debugger.server.services.bin;

import java.lang.reflect.InvocationTargetException;
import com.rinit.debugger.server.client.IClient;
import com.rinit.debugger.server.dto.FileDTO;
import com.rinit.debugger.server.file.ProcessDriver;
import com.rinit.debugger.server.file.bin.AbstractBin;
import com.rinit.debugger.server.services.interfaces.IFileService;

public class BinRunner implements Runnable {

	private Class<? extends AbstractBin> binClass;
	private AbstractBin binInstance;
	
	private IClient client;
	private IFileService fileService;
	
	public BinRunner(Class<? extends AbstractBin> binClass) {
		this.binClass = binClass;
		this.createBinClass();
	}
	
	public String getStartUpMessage() {
		return this.binInstance.getStarterMessage();
	}
	
	public void setClient(IClient client) {
		this.client = client;
		this.fileService = this.client.getFileService();
		this.binInstance.setClient(client);
	}
	
	public void setParams(String[] params) {
		this.binInstance.setParams(params);
	}
	
	@Override
	public void run() {		
		this.binInstance.run();
		this.deleteProcessFile();
	}
	
	private void createBinClass() {
		try {
			this.binInstance = this.binClass.getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}
	
	private void deleteProcessFile() {
		this.fileService.deleteFile(this.getProcessFile());
	}
	
	private FileDTO getProcessFile() {
		return this.fileService.getFileByPathAndName(ProcessDriver.PATH, Long.toString(Thread.currentThread().getId())).get(0);
	}

}
