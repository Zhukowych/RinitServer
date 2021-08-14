package com.rinit.debugger.server.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Table("files")
@Builder
@Getter
@Setter
public class FileEntity {
	
	@Id
	private Long id;
	private String name;
	private String extention;
	private String path;
	private int position;
	private String content;
	

}
