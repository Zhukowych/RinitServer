package com.rinit.debugger.server.file;

import com.rinit.debugger.server.dto.FileDTO;

public abstract class AbstractDriver {

	protected FileDTO dto = new FileDTO();
	
	public boolean isDirable() {
		return false;
	}
	
	public void fromDTO(FileDTO dto) {
		this.dto = dto;
		this.buildFromDTO();
	}

	public FileDTO toDTO() {
		this.dto.setContent(this.buildContent());
		return this.dto;
	}
	
	public FileDTO getDto() {
		return dto;
	}

	public void setDto(FileDTO dto) {
		this.dto = dto;
	}

	public String getName() {
		return this.dto.getName();
	}

	public void setName(String name) {
		this.dto.setName(name);
	}

	public String getExtention() {
		return this.dto.getExtention();
	}

	public void setExtention(String extention) {
		this.dto.setExtention(extention);
	}

	public String getPath() {
		return this.dto.getPath();
	}

	public void setPath(String path) {
		this.dto.setPath(path);
	}

	public int getPosition() {
		return this.dto.getPosition();
	}

	public void setPosition(int position) {
		this.dto.setPosition(position);
	}

	public String getContent() {
		return this.dto.getContent();
	}

	public void setContent(String content) {
		this.dto.setContent(content);
	}
	
	public String getChildrenPath() {
		return this.dto.getChildrenPath();
	}
	
	public String getFullName() {
		return this.dto.getFullName();
	}
	
	protected abstract void buildFromDTO();
	public abstract String buildContent();
	
}
