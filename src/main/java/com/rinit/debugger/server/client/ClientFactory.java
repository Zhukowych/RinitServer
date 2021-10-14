package com.rinit.debugger.server.client;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ClientFactory {
	
	private String serverAddress;

	@Value("${server.port}")
	private int port;
	
	public ClientFactory() {
		try {
			this.serverAddress = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	public IClient newInstance() {
		return new RinitClient(this.getServerHost());
	}
	
	private String getServerHost() {
		return String.format("http://%s:%d", this.serverAddress, this.port);
	}
}
