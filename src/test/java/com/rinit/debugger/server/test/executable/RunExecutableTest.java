package com.rinit.debugger.server.test.executable;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.rinit.debugger.server.services.interfaces.IExecutableService;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class RunExecutableTest {
	
	@Autowired
	private IExecutableService executableService;
	
	@Test	
	public void runExecutableTest() {
		executableService.runExecutableWithName("test_executable");
	}
	
	
}
