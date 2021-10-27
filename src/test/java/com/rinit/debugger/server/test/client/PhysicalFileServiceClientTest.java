package com.rinit.debugger.server.test.client;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import com.rinit.debugger.server.client.RinitClient;
import com.rinit.debugger.server.exception.ServiceException;
import com.rinit.debugger.server.file.pfille.PhysicalFileDriver;
import com.rinit.debugger.server.services.interfaces.IPhysicalFileService;
import com.rinit.debugger.server.utils.FileToBytesConverter;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PhysicalFileServiceClientTest {

	@LocalServerPort
	private int port;
	
	public String rinitServiceHost = "http://localhost:%d";


	
    @Test
	public void loadFileTest() throws ServiceException {
    	byte[] b = new FileToBytesConverter("C:\\Users\\Dell\\eclipse-workspace\\Rinit\\src\\test\\resources\\test_bin.jar").getBytes();
    	MockMultipartFile file = new MockMultipartFile(
    	        "test_bin.jar", 
    	        "test_bin.jar", 
    	        MediaType.TEXT_PLAIN_VALUE,
    	        b
    	        );
		RinitClient client = new RinitClient(this.getServiceHost());
		IPhysicalFileService physicalFileService = client.getPhysicalServiceClient();
		PhysicalFileDriver driver = PhysicalFileDriver.builder().name("test_bin").build();
		physicalFileService.uploadFile(driver, file);
	}
	
	private String getServiceHost() {
		return String.format(rinitServiceHost, port);
	}
	
}
