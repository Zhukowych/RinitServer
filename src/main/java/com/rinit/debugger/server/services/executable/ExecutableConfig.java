package com.rinit.debugger.server.services.executable;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class ExecutableConfig {
	
	@Value("${executor.corePoolSize}")
	private int corePoolSize;
	
	@Value("${executor.maxPoolSize}")
	private int maxPoolSize;
	
	@Value("${executor.queueCapacity}")
	private int queueCapacity;
	
	
	@Bean
	@Scope("singleton")
	public Executor getExecutor() {
	    Executor executor = new Executor();
	    executor.setMaxThreads(corePoolSize);
	    return executor;
	}
	
}
