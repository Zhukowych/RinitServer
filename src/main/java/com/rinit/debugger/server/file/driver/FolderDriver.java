package com.rinit.debugger.server.file.driver;

import com.rinit.debugger.server.dto.FileDTO;
import com.rinit.debugger.server.entity.IFileDriver;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FolderDriver implements IFileDriver {

	private final static String EXTENTION = "directory";
	
	private String name;
	private String path;
	private int position;
	
	@Override
	public void fromDTO(FileDTO dto) {
		this.name = dto.getName();
		this.path = dto.getPath();
		this.position = dto.getPosition();
	}

	@Override
	public FileDTO toDTO() {
		FileDTO dto = FileDTO.builder().name(this.name).extention(EXTENTION).path(this.path).position(position).build();
		return dto;
	}

	@Override
	public String getContent() {
		// TODO Auto-generated method stub
		return null;
	}

}
