package com.rinit.debugger.server.file.bin;

import com.rinit.debugger.server.client.IClient;

public abstract class AbstractBin {
	
	protected IClient client;
	protected String[] params;
	
	public void setClient(IClient client) {
		this.client = client;
	}
	
	public void setParams(String[] params) {
		this.params = params;
	}
	
	public abstract void run();
	public abstract String getStarterMessage();
	
}
