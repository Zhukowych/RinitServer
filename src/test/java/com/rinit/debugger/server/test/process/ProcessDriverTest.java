package com.rinit.debugger.server.test.process;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import com.rinit.debugger.server.core.Extentions;
import com.rinit.debugger.server.dto.FileDTO;
import com.rinit.debugger.server.file.process.ProcessDriver;

public class ProcessDriverTest {
	
	public static final String testContent = "<process><pid>123</pid><executableName>test</executableName></process>";
	
	@Test
	public void testCreateProcessDriverFromDTO() {
		FileDTO dto = FileDTO.builder().content(testContent).build();
		ProcessDriver process = new ProcessDriver();
		process.fromDTO(dto);
		assertEquals(process.getPid(), 123);
		assertEquals(process.getExecutableName(), "test");
	}
	
	@Test
	public void testCreateDTOfromProcessDriver() {
		ProcessDriver process = new ProcessDriver(123, "test");
		FileDTO dto = process.toDTO();
		assertEquals(dto.getName(), "123");
		assertEquals(dto.getExtention(), Extentions.PROCESS);
		assertEquals(dto.getContent(), testContent);
	}
	
	
}
