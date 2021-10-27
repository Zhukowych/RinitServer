package com.rinit.debugger.server.test.bin;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;

import com.rinit.debugger.server.client.RinitClient;
import com.rinit.debugger.server.dto.FileDTO;
import com.rinit.debugger.server.exception.ServiceException;
import com.rinit.debugger.server.file.bin.BinDriver;
import com.rinit.debugger.server.file.library.ClassToLoadInfo;
import com.rinit.debugger.server.file.library.LibraryDriver;
import com.rinit.debugger.server.file.pfille.PhysicalFileDriver;
import com.rinit.debugger.server.services.interfaces.IFileService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class LoadBinTest {
	
	@LocalServerPort
	private int port;
	
	public String rinitServiceHost = "http://localhost:%d";

	
	@Test
	public void loadBinTest() {
		RinitClient client = new RinitClient(this.getServiceHost());
		IFileService fileService = client.getFileService();
		FileDTO file = fileService.getFileByPathAndName("/physical_files/", "test_bin_jar").get(0);
		PhysicalFileDriver pfile = new PhysicalFileDriver();
		pfile.fromDTO(file);
		LibraryDriver library = new LibraryDriver();
		library.setName("test_bin");
		library.setPhysicalFile(pfile);
		library.addClassToLoad(new ClassToLoadInfo("test_bin", "com.rinit.debugger.server.test.bin.TestBin"));
		FileDTO libraryFile = library.toDTO();
		libraryFile.setPath("/lib/bin/");
		try {
			fileService.createFile(libraryFile);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	
//		BinDriver bin = new BinDriver();
//		bin.setName("test_bin");
//		bin.setBinLibraryPath("/lib/bin/");
//		bin.setBinLibraryName("test_bin");
//		FileDTO binDTO = bin.toDTO();
//		binDTO.setPath("/bin/");
//		try {
//			fileService.createFile(binDTO);
//		} catch (ServiceException e) {
//			e.printStackTrace();
//		}
	}
	
	private String getServiceHost() {
		return String.format(rinitServiceHost, port);
	}

}
