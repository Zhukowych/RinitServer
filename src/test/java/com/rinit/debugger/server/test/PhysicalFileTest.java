package com.rinit.debugger.server.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Random;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import com.rinit.debugger.server.controller.PhysicalFileController;
import com.rinit.debugger.server.utils.FileToBytesConverter;

@SpringBootTest
@AutoConfigureMockMvc
public class PhysicalFileTest {
	
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void uploadFileTest() throws Exception {
		String testFileName = this.createTestFileName();
	    MockMultipartFile file = new MockMultipartFile(
	        "file", 
	        testFileName, 
	        MediaType.TEXT_PLAIN_VALUE, 
	        new FileToBytesConverter("src/test/resources/test_executable.jar").getBytes()
	    	);
	    
		this.mockMvc.perform(multipart(PhysicalFileController.LOAD_FILE_URL)
								.file(file)
								.param("name", testFileName)	
							)
							.andDo(print())					
							.andExpect(status().isOk());
						    
	}
	
	private String createTestFileName() {
		Random random = new Random();
		return String.format("test_name_%d", random.nextInt());
	}
	
}
