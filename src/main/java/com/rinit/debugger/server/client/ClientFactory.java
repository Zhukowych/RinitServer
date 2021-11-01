package com.rinit.debugger.server.client;

import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.stereotype.Component;

@Component
public class ClientFactory {
	
	private String serverAddress;

	private int port;	
		
	public ClientFactory() {
		this.serverAddress = "localhost";	

	}
	
	public void setPort(int port) {
		this.port = port;
	}
	
	public IClient newInstance() {
		return new RinitClient(this.getServerHost());
	}
	
	public void onApplicationEvent(WebServerInitializedEvent event) {
	    Integer port = event.getWebServer().getPort();
	    this.port = port;
	}
	
	private String getServerHost() {
		return String.format("http://%s:%d", this.serverAddress, this.port);
	}
	
}
