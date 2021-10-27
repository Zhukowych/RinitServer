package com.rinit.debugger.server.test.executable;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.rinit.debugger.server.services.interfaces.IBinService;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class RunExecutableTest {
	
	@Autowired
	private IBinService executableService;
	
	@Test	
	public void runExecutableTest() {
		executableService.runBinWithName("test_executable");
	}
	
	
}
