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
	
}
