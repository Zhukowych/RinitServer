package com.rinit.debugger.server.file.executable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.rinit.debugger.server.client.IClient;
import com.rinit.debugger.server.dto.FileDTO;
import com.rinit.debugger.server.file.IFileDriver;

public class ExecutableDriver implements IFileDriver {

	private FileDTO dto;
	private String name;
	private String executableLibraryPath;
	private String executableLibraryName;
	private Class<?> executableClass;
	private IClient client;
	
	@Override
	public void fromDTO(FileDTO dto) {
		ExecutableImporter importer = new ExecutableImporter();
		importer.parse(dto.getContent());
		this.executableLibraryName = importer.getLibraryName();
		this.executableLibraryPath = importer.getLibraryPath();
		this.name = dto.getName();
		this.dto = dto;
	}

	@Override
	public FileDTO toDTO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getContent() {
		return this.dto.getContent();
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getExecutableLibraryName() {
		return this.executableLibraryName;
	}
	
	public String getExecutableLibraryPath() {
		return this.executableLibraryPath;
	}

	public void setExecutableClass(Class<?> executableClass) {
		this.executableClass = executableClass;
	}
	
	public void setClient(IClient client) {
		this.client = client;
	}
	
	public void run() {
		Object executable = null;
		Method executeMethod = null;
		Method setClientMethod = null;
		try {
			executable = this.executableClass.getDeclaredConstructors()[0].newInstance();
			try {
				executeMethod = this.executableClass.getDeclaredMethod("execute");
				setClientMethod = this.executableClass.getDeclaredMethod("setEnv", IClient.class);
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			setClientMethod.invoke(executable, this.client);
			executeMethod.invoke(executable);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
