package com.rinit.debugger.server.utils;

import java.io.File;
import java.io.FileInputStream;

public class FileToBytesConverter {
	
	private File file;
	
	public FileToBytesConverter(String path) {
		this.file = new File(path);
	}
	
	public byte[] getBytes() {
		FileInputStream inputStream = null;
		
		byte[] fileBytes = new byte[(int)this.file.length()];
		try {
			inputStream = new FileInputStream(this.file);
			inputStream.read(fileBytes);
			inputStream.close();
		} catch(Exception e) {
			try { inputStream.close(); } catch (Exception ex) {}
			e.printStackTrace();
		}
		return fileBytes;
	}
}
