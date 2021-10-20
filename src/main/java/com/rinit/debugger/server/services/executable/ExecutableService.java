package com.rinit.debugger.server.services.executable;

import java.lang.reflect.Executable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.rinit.debugger.server.core.Extentions;
import com.rinit.debugger.server.dto.FileDTO;
import com.rinit.debugger.server.file.executable.ExecutableDriver;
import com.rinit.debugger.server.file.library.LibraryClassNotFoundException;
import com.rinit.debugger.server.file.library.LibraryDriver;
import com.rinit.debugger.server.file.library.LibraryNotFoundException;
import com.rinit.debugger.server.file.process.ProcessDriver;
import com.rinit.debugger.server.services.interfaces.IExecutableService;
import com.rinit.debugger.server.services.interfaces.IFileService;
import com.rinit.debugger.server.services.interfaces.ILibraryService;

@Service
public class ExecutableService implements IExecutableService {

	@Autowired
	private IFileService fileService;
	
	@Autowired
	private ILibraryService libraryService;
	
	@Autowired
	private Executor executor;
	
	private Object lock = new Object();
	private Map<Integer, ProcessDriver> runningExecutables = new HashMap<Integer, ProcessDriver>();
	
	private Map<String, ExecutableDriver> executables = new HashMap<String, ExecutableDriver>();

	@PostConstruct
	private void config() {
		//ExecutableConfigurer configurer = new ExecutableConfigurer(this);
		//configurer.config();
	}
	
	@Override
	public synchronized void loadExecutables() {
		this.createExecutablesMap();
	}
	
	@Override
	public void runExecutableWithName(String name) {
		synchronized (lock) {
			ExecutableDriver executable = executables.get(name);
			if (executable != null) {
				this.executor.execute(executable);
			} else {
				System.out.println("there is no executable with name");
			}
		}
	}

	@Override
	public void killProcess(String kill) {
		synchronized (lock) {
			ProcessDriver process = new ProcessDriver();
			
		}
	}
	
	private void createExecutablesMap() {
		List<String> dirs = fileService.getAllChildrenDirs("/bin/", Extentions.DIRECTORY);
		dirs.add("/bin/");
		for(String dir : dirs) {
			this.addDirExecutablesToMap(dir);
		}
	}
	
	private void addDirExecutablesToMap(String dir) {
		List<FileDTO> fileDTOs = fileService.getFilesByPathAndExtention(dir, Extentions.EXECUTABLE);
		for (FileDTO dto : fileDTOs) {
			ExecutableDriver executable = this.createExecutable(dto);
			this.executables.put(executable.getName(), executable);
		}
	}
	
	private ExecutableDriver createExecutable(FileDTO dto) {
		ExecutableDriver executable = new ExecutableDriver();
		executable.fromDTO(dto);
		LibraryDriver library = null;
		try {
			library = libraryService.getLibraryByPathName(executable.getExecutableLibraryPath(), executable.getExecutableLibraryName());
		} catch (LibraryNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			executable.setExecutableClass(library.getClassWithName(executable.getName()));
		} catch (LibraryClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return executable;
	}
	
}
