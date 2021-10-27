package com.rinit.debugger.server.test.bin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.rinit.debugger.server.services.interfaces.IBinService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RunBinTest {
	
	@Autowired
	private IBinService binService;
	
	@Test
	public void runBinTest() {
		this.binService.runBinWithName("test_bin");
	}

}
