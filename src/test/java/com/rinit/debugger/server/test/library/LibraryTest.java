package com.rinit.debugger.server.test.library;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.rinit.debugger.server.dto.FileDTO;
import com.rinit.debugger.server.file.driver.PhysicalFileDriver;
import com.rinit.debugger.server.file.library.ClassToLoadInfo;
import com.rinit.debugger.server.file.library.LibraryDriver;

public class LibraryTest{
	
	public final static String testContent = "<library><physicalFile><physicalLocation>C:\\Users\\Dell\\eclipse-workspace\\Rinit\\upload\\test_executable.jar</physicalLocation></physicalFile><classesToLoad><loadClass><name>test_executable</name><path>com.rinit.debugger.server.test.TestExecutable</path></loadClass></classesToLoad></library>\r\n";
		
	@Test
	public void libraryToContextnTest() {
		List<ClassToLoadInfo> loadClass = new ArrayList<ClassToLoadInfo>();
		loadClass.add(ClassToLoadInfo.builder().name("excel file extention").path("com.test.Class").build());
		PhysicalFileDriver physicalFile = PhysicalFileDriver.builder().name("test").filePath("C://asd/asd.asd").build();
		LibraryDriver library = new LibraryDriver();
		library.setName("request");
		library.setPhysicalFile(physicalFile);
		library.setClassesToLoad(loadClass);
	}
	
	@Test
	public void libraryCreationTest() {
		FileDTO dto = FileDTO.builder().content(testContent).build();
		LibraryDriver library =  new LibraryDriver();
		library.fromDTO(dto);
	}
	
	@Test
	public void libraryClassLoadingTest() {
		FileDTO dto = FileDTO.builder().content(testContent).build();
		LibraryDriver library =  new LibraryDriver();
		library.fromDTO(dto);
		library.loadClasses();
		assertNotEquals(library.getLoadedClasses().get("test"), null);
		assertTrue(library.getLoadedClasses().size() > 0);
	}

}
