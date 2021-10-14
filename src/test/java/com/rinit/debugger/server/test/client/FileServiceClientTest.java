package com.rinit.debugger.server.test.client;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;

import com.rinit.debugger.server.client.RinitClient;
import com.rinit.debugger.server.dto.FileDTO;
import com.rinit.debugger.server.exception.ServiceException;
import com.rinit.debugger.server.services.interfaces.IFileService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class FileServiceClientTest {
	
	@LocalServerPort
	private int port;
	
	public String rinitServiceHost = "http://localhost:%d";
	
	@Test
	public void createFileWithFileServiceClientTest() throws InterruptedException {
		RinitClient client = new RinitClient(this.getServiceHost());
		IFileService fileService = client.getFileService();
		FileDTO file = FileDTO.builder().name("13123").path("123123").build();
		FileDTO savedFile = null;
		try {
			savedFile = fileService.createFile(file);
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		assertNotNull(savedFile);
		assertTrue(savedFile.getId() != null);
	}
	
	@Test
	public void getFilesByPathTest() throws Exception {
		RinitClient client = new RinitClient(this.getServiceHost());
		IFileService fileService = client.getFileService();
		List<FileDTO> files = fileService.getFilesByPath("/");
		System.out.println(files);
	}
	
	private String getServiceHost() {
		return String.format(rinitServiceHost, port);
	}
}
