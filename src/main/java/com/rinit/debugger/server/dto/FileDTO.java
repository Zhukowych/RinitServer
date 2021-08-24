package com.rinit.debugger.server.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class FileDTO {
	
	private Long id;
	private String name;
	private String extention;
	private String path;
	private int position;
	private String content;
	
	public String getChildrenPath() {
		return String.format("%s%s/", this.path, this.name);
	}
	
	public void write(String string) {
		if (this.content == null)
			this.content = "";
		this.content += string;
	}
	
	public void clear() {
		this.content = "";
	}
}
