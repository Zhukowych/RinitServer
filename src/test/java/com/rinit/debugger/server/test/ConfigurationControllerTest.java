package com.rinit.debugger.server.test;


import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.rinit.debugger.server.controller.ConfigurationController;

@SpringBootTest
@AutoConfigureMockMvc
public class ConfigurationControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void createMainFileStructureTest() throws Exception {
		this.mockMvc.perform(post(ConfigurationController.INIT_CONFIGURE))
					.andDo(print())
					.andExpect(status().isOk())
					.andExpect(content().string(containsString("succes")));
	}
}
