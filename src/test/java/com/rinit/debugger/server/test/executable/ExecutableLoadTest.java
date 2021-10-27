package com.rinit.debugger.server.test.executable;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rinit.debugger.server.controller.urls.FileControllerUrls;
import com.rinit.debugger.server.core.Extentions;
import com.rinit.debugger.server.dto.FileDTO;

@SpringBootTest
@AutoConfigureMockMvc
public class ExecutableLoadTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	public void loadExecutableTest() throws Exception {
		FileDTO dto = FileDTO.builder().content(ExecutableDriverTest.testContent).name("test_executable").path("/bin/").extention(Extentions.BIN).build();
		this.mockMvc.perform(post(FileControllerUrls.CREATE_FILE_URL)
				.contentType("application/json")
				.content(objectMapper.writeValueAsBytes(dto))
				).andExpect(status().isOk());
	}

}
