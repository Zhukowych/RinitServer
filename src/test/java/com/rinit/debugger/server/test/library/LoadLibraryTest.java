package com.rinit.debugger.server.test.library;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rinit.debugger.server.controller.FilesController;
import com.rinit.debugger.server.dto.FileDTO;
import com.rinit.debugger.server.file.library.LibraryDriver;

/* 
 * test for uploading test liba
 * */
@SpringBootTest
@AutoConfigureMockMvc
public class LoadLibraryTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;

	
	@Test
	public void loadLibraryTest() throws Exception {
		FileDTO dto = FileDTO.builder().content(LibraryTest.testContent).name("test_executable").path("/lib/bin/").extention(LibraryDriver.EXTENTION).build();
		this.mockMvc.perform(post(FilesController.CREATE_FILE_URL)
				.contentType("application/json")
				.content(objectMapper.writeValueAsBytes(dto))
				).andExpect(status().isOk());
	}
}
