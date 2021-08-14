package com.rinit.debugger.server.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
	
	 @ExceptionHandler(Exception.class)
	 public ResponseEntity<String> error(Exception ex) {
		 return new ResponseEntity <>(ex.getMessage(), HttpStatus.NOT_FOUND);
	 }
}
