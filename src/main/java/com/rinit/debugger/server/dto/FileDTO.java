
package com.rinit.debugger.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
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
	
	public String getFullName() {
		return String.format("%s%s.%s", this.path, this.name, this.extention);
	}
	
	public void write(String string) {
		if (this.content == null)
			this.content = "";
		this.content += string;
	}
	
	public void cwrite(String string) {
		this.clear();
		this.write(string);
	}
	
	public void clear() {
		this.content = "";
	}
}
