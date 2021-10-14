package com.rinit.debugger.server.services.executable;

import java.util.LinkedList;
import java.util.Queue;

import org.springframework.beans.factory.annotation.Autowired;

import com.rinit.debugger.server.client.ClientFactory;
import com.rinit.debugger.server.file.executable.ExecutableDriver;
import com.rinit.debugger.server.file.process.ProcessDriver;

public class Executor {

	@Autowired
	private ClientFactory clientFactory;
	
	private int maxThreads;
	
	private int maxPID = 1;
	
	private Object lock = new Object();
	
	private int currentQueueLength = 0;
	private Queue<ProcessDriver> processes = new LinkedList<ProcessDriver>();
	
	public Executor() {
	}
	
	public void setMaxThreads(int maxThreads) {
		this.maxThreads = maxThreads;
	}
	
	public void execute(ExecutableDriver executable) {
		synchronized (lock) {
			this.maxPID++;
			ProcessDriver process = new ProcessDriver();
			process.setPid(maxPID);
			process.setClient(clientFactory.newInstance());
			process.setExecutable(executable);
			process.start();
			this.processes.add(process);
		}
	}
	
}
