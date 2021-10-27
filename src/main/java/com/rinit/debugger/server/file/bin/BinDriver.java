package com.rinit.debugger.server.file.bin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.rinit.debugger.server.client.IClient;
import com.rinit.debugger.server.core.Extentions;
import com.rinit.debugger.server.dto.FileDTO;
import com.rinit.debugger.server.file.IFileDriver;

public class BinDriver implements IFileDriver {

	private String name;
	private String binLibraryPath;
	private String binLibraryName;
	
	@Override
	public void fromDTO(FileDTO dto) {
		BinImporter importer = new BinImporter(this);
		importer.parse(dto.getContent());
	}

	@Override
	public FileDTO toDTO() {
		FileDTO dto = FileDTO.builder().name(this.name).content(this.getContent()).extention(Extentions.BIN).build();
		return dto;
	}

	@Override
	public String getContent() {
		BinExporter exporter = new BinExporter(this);
		return exporter.export();
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getBinLibraryName() {
		return this.binLibraryName;
	}
	
	public String getBinLibraryPath() {
		return this.binLibraryPath;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setBinLibraryPath(String binLibraryPath) {
		this.binLibraryPath = binLibraryPath;
	}

	public void setBinLibraryName(String binLibraryName) {
		this.binLibraryName = binLibraryName;
	}
	
}
