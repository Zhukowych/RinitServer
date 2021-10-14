package com.rinit.debugger.server.test.executable;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;

import com.rinit.debugger.server.dto.FileDTO;
import com.rinit.debugger.server.file.executable.ExecutableDriver;

public class ExecutableDriverTest {
	
	public static String testContent = "<executable><library><libraryPath>/lib/bin/</libraryPath><libraryName>test_executable</libraryName></library></executable>";
	
	@Test
	public void executableDriverTest() {
		FileDTO dto = FileDTO.builder().name("test_exe").content(testContent).build();
		ExecutableDriver executable = new ExecutableDriver();
		executable.fromDTO(dto);
		assertNotNull(executable.getExecutableLibraryName());
		assertNotNull(executable.getExecutableLibraryPath());
	}
}
