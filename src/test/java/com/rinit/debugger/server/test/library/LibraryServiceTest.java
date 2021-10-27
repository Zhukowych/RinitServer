package com.rinit.debugger.server.test.library;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.rinit.debugger.server.exception.ServiceException;
import com.rinit.debugger.server.file.library.LibraryDriver;
import com.rinit.debugger.server.file.library.LibraryNotFoundException;
import com.rinit.debugger.server.services.interfaces.ILibraryService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class LibraryServiceTest {

	@Autowired
	private ILibraryService libraryService;
	
	@Test
	public void libraryDevPathesTest() {
		System.out.println(this.libraryService.getLocatedPathes());
	}
	
	@Test
	public void libraryDevNamesByPathesTest() {
		try {
			System.out.println(this.libraryService.getLibrariesNamesByPath("/lib/bin/"));
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void getLibraryByPathName() throws LibraryNotFoundException {
		LibraryDriver library = this.libraryService.getLibraryByPathName("/lib/bin/", "test_lib_dev");
		System.out.println(library.getLoadedClasses());
	}
	
}
