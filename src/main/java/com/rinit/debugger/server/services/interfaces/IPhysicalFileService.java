package com.rinit.debugger.server.services.interfaces;

import org.springframework.web.multipart.MultipartFile;
import com.rinit.debugger.server.exception.ServiceException;
import com.rinit.debugger.server.file.driver.PhysicalFileDriver;

public interface IPhysicalFileService {
	public PhysicalFileDriver uploadFile(PhysicalFileDriver physicalFile, MultipartFile file) throws ServiceException;
}
