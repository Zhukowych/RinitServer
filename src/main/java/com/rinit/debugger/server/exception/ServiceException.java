package com.rinit.debugger.server.exception;


public class ServiceException extends Exception {
	
	public ServiceException(String message) {
		super(message);
	}
	
	public ServiceException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
