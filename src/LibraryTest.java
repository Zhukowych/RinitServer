package com.rinit.debugger.server.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.rinit.debugger.server.file.driver.LibraryDriver;
import com.rinit.debugger.server.file.driver.PhysicalFileDriver;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LibraryTest {
	
	
	@Test
	public void libraryCreationTest() {
		System.out.println(123123);
		PhysicalFileDriver physicalFile = PhysicalFileDriver.builder().name("test").filePath("C://asd/asd.asd").build();
		LibraryDriver library = LibraryDriver.builder().name("request").physicalFile(physicalFile).build();
		System.out.println(library.getContent());
	}

}
