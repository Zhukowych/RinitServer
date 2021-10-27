package com.rinit.debugger.server.dev.core;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class DevConfiguration {
	
	@Value("${dev}")
	private boolean isDev;
	
	public boolean isDev() {
		return this.isDev;
	}
	
}
