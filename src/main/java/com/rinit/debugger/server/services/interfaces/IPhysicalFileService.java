package com.rinit.debugger.server.services.interfaces;

import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import com.rinit.debugger.server.exception.ServiceException;
import com.rinit.debugger.server.file.pfille.PhysicalFileDriver;

public interface IPhysicalFileService {
	public PhysicalFileDriver uploadFile(PhysicalFileDriver physicalFile, MultipartFile file) throws ServiceException;
	public InputStream getPfileByPhysicalPath(String ppath) throws ServiceException;
}
