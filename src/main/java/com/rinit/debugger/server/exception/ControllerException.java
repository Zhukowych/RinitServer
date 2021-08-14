package com.rinit.debugger.server.exception;

public class ControllerException extends Exception {
	
	public ControllerException(String message) {
		super(message);
	}
	
	public ControllerException(String message, Throwable ex) {
		super(message, ex);
	}
}
