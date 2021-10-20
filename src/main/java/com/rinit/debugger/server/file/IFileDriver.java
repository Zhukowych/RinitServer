package com.rinit.debugger.server.file;

import com.rinit.debugger.server.dto.FileDTO;

public interface IFileDriver {
	
	public void fromDTO(FileDTO dto);
	public FileDTO toDTO();
	public String getContent();
	
}
