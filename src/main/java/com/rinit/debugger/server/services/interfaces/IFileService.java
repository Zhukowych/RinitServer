package com.rinit.debugger.server.services.interfaces;

import java.util.List;

import com.rinit.debugger.server.dto.FileDTO;
import com.rinit.debugger.server.exception.ServiceException;

public interface IFileService {
	public FileDTO saveFile(FileDTO file) throws ServiceException;
	
	public FileDTO createFile(FileDTO file) throws ServiceException;
	public FileDTO createOrCheckFile(FileDTO dto) throws ServiceException;

	public List<FileDTO> getFilesByPath(String path);
	public List<FileDTO> getFilesByPathAndExtention(String path, String extention);
	public List<FileDTO> getFileByPathAndName(String path, String name);
	
	public void deleteFile(FileDTO dto);
	public void deleteAllChildrenOfPath(String path) throws ServiceException;

	public boolean isFileExists(FileDTO dto);
	public List<String> getAllChildrenDirs(String baseDir, String extention);
}
