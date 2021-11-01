package com.rinit.debugger.server.test.bin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;

import com.rinit.debugger.server.client.ClientFactory;
import com.rinit.debugger.server.dto.FileDTO;
import com.rinit.debugger.server.exception.ServiceException;
import com.rinit.debugger.server.services.interfaces.IBinService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RunBinTest {
	
	@Autowired
	private IBinService binService;
	
	@Autowired
	private ClientFactory clientFactory;
	
	@LocalServerPort
	private int port;
	
	@Test
	public void runBinTest() throws ServiceException, InterruptedException {
		this.clientFactory.setPort(this.port);
		FileDTO dto = this.binService.runBinWithName("test_bin_dev", new String[] {});
		Thread.sleep(4000);
	}

}
