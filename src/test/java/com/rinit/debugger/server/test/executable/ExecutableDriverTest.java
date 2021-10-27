package com.rinit.debugger.server.test.executable;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;

import com.rinit.debugger.server.dto.FileDTO;
import com.rinit.debugger.server.file.bin.BinDriver;

public class ExecutableDriverTest {
	
	public static String testContent = "<executable><library><libraryPath>/lib/bin/</libraryPath><libraryName>test_executable</libraryName></library></executable>";
	
	@Test
	public void executableDriverTest() {
		FileDTO dto = FileDTO.builder().name("test_exe").content(testContent).build();
		BinDriver executable = new BinDriver();
		executable.fromDTO(dto);
		assertNotNull(executable.getBinLibraryName());
		assertNotNull(executable.getBinLibraryPath());
	}
}
