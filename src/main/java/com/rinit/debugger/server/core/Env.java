package com.rinit.debugger.server.core;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.rinit.debugger.server.services.interfaces.IFileService;
import com.rinit.debugger.server.services.interfaces.ILibraryService;

@Component("Env")
public class Env implements ApplicationContextAware {
	
	private static ApplicationContext applicatonContext;
	
	public static IFileService getFileService() {
		return Env.applicatonContext.getBean(IFileService.class);
	}

	public static ILibraryService getLibraryService() {
		return Env.applicatonContext.getBean(ILibraryService.class);
	}
	
	public static Object getBean(Class<?> bean){
		return Env.applicatonContext.getBean(bean);
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		Env.applicatonContext = applicationContext;
	}
}
