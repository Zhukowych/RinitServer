package com.rinit.debugger.server.test.executable;

import com.rinit.debugger.server.client.IClient;
import com.rinit.debugger.server.core.Env;

public class TestExecutable {
	
	private IClient client;
	
	public void setEnv(IClient client) {
		this.client = client;
	}
	
	public void execute() {
		System.out.println("I'm called");
	}
}
