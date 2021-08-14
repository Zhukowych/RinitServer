package com.rinit.debugger.server.test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.rinit.debugger.server.dto.FileDTO;
import com.rinit.debugger.server.file.driver.LibraryDriver;
import com.rinit.debugger.server.file.driver.PhysicalFileDriver;
import com.rinit.debugger.server.file.library.ClassToLoadInfo;

public class LibraryTest{
	
	private String testContent = "<library><physicalFile><physicalLocation>C://asd/asd.asd</physicalLocation></physicalFile><classesToLoad><loadClass><name>ads</name><path>com.rinit.debugger.server.utils.ExceptionUtils</path></loadClass></classesToLoad></library>\r\n";
		
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
		assertTrue(library.getLoadedClassed().size() > 0);
	}

}
