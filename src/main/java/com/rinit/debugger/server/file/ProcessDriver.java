package com.rinit.debugger.server.file;

import com.rinit.debugger.server.core.Extentions;
import com.rinit.debugger.server.dto.FileDTO;

public class ProcessDriver implements IFileDriver {

	private long pid;
	private String message;
	public static final String PATH = "/run/proc/";
	
	public void setPid(long pid) {
		this.pid = pid;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public void fromDTO(FileDTO dto) {

		// TODO Auto-generated method stub
		
	}

	@Override
	public FileDTO toDTO() {
		return FileDTO.builder()
				.name(Long.toString(this.pid))
				.extention(Extentions.PROCESS)
				.path(ProcessDriver.PATH)
				.content(this.getContent())
				.build();
	}

	@Override
	public String getContent() {
		return this.message;
	}

}
