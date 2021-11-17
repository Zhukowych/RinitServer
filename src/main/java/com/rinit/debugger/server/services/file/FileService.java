package com.rinit.debugger.server.services.file;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rinit.debugger.server.dto.FileDTO;
import com.rinit.debugger.server.dto.FileDTOMapper;
import com.rinit.debugger.server.entity.FileEntity;
import com.rinit.debugger.server.exception.ServiceException;
import com.rinit.debugger.server.file.AbstractDriver;
import com.rinit.debugger.server.repository.FileRepository;
import com.rinit.debugger.server.services.interfaces.IFileService;
import com.rinit.debugger.server.utils.ExceptionUtils;
import com.rinit.debugger.server.utils.FileDTOUtils;

@Service
public class FileService implements IFileService {

	@Autowired
	private FileRepository repository;
	
	@Autowired
	private FileDTOMapper mapper;
	
	private static final Logger logger = LoggerFactory.getLogger(FileService.class);

	@PostConstruct
	private void configureFileService() {
		FileServiceConfigurer configurer = new FileServiceConfigurer(this);
		configurer.configure();
	}
	
	@Override
	public FileDTO saveFile(FileDTO file) throws ServiceException {
//		if(!this.isFileExists(file)) {
//			throw new ServiceException(String.format("Thre is no file with path %s and %s", file.getPath(), file.getName()));
//		}
		return this.saveDTO(file);
	}
	
	@Override
	public FileDTO createFile(FileDTO file) throws ServiceException {
		if(this.isFileExists(file)) {
			throw new ServiceException(String.format("File with name %s already exists", file.getName()));
		}
		return this.saveDTO(file);
	}
	
	
	@Override
	public FileDTO createOrCheckFile(FileDTO dto) throws ServiceException {
		if (!this.isFileExists(dto)) {
			return this.createFile(dto);
		}
		return dto;
	}

	@Override
	public void copyFile(FileDTO dto, String destination) {
		System.out.println(dto.getPath());
		System.out.println(dto.getName());
		System.out.println(destination);
		this.repository.copyFile(dto.getPath(), dto.getName(), destination);
		this.repository.copyAll(dto.getChildrenPath(), destination);
	}

	@Override
	public void renMove(FileDTO dto, String destination) {
		this.repository.copyFile(dto.getPath(), dto.getName(), destination);
		this.repository.copyAll(dto.getChildrenPath(), destination);
		this.deleteFile(dto);
		try {
			this.deleteAllChildrenOfPath(dto.getChildrenPath());
		} catch (ServiceException e) {e.printStackTrace();}
	}
	
	@Override
	public List<FileDTO> getFilesByPath(String path) {
		List<FileEntity> entities = this.repository.getFilesByPath(path);
		return mapper.entityToDtoList(entities);
	}
	
	@Override
	public List<FileDTO> getFilesByPathAndExtention(String path, String extention) {
		List<FileEntity> entities = this.repository.getFilesByPathExtention(path, extention);
		return mapper.entityToDtoList(entities);
	}
	
	@Override
	public List<FileDTO> getFileByPathAndName(String path, String name) {
		List<FileEntity> entities = this.repository.getFileByPathAndName(name, path);
		return mapper.entityToDtoList(entities);
	}
	
	@Override
	public List<FileDTO> getFilesByParentPathExtention(String parentPath, String extention) {
		List<FileEntity> entities = this.repository.getFilesByParentPathExtention(parentPath, extention);
		return mapper.entityToDtoList(entities);
	}
	
	@Override
	public void deleteFile(FileDTO dto) {
		FileEntity entity = mapper.dtoToEntity(dto);
		repository.delete(entity);
	}
	
	
	@Override
	public void deleteAllChildrenOfPath(String path) throws ServiceException {
		try {
			repository.deleteAllChildrensOfPath(path); 
		} catch(Exception ex) {
			logger.error(ExceptionUtils.stackTraceToString(ex));
			throw new ServiceException(ex.getMessage(), ex);
		}
	}
	
	/// UTIL ///
	
	@Override
	public boolean isFileExists(FileDTO dto) {
		if( this.repository.getFilesByNameExtentionPath(dto.getName(), dto.getExtention(), dto.getPath()).size() == 0 )
			return false;
		else
			return true;
	}
	
	@Override
	public List<String> getAllChildrenDirs(String baseDir, String extention) {
		List<String> paths  = FileDTOUtils.getChildrenPathsList(this.getFilesByPathAndExtention(baseDir, "directory"));
		int i = 0;
		while( i < paths.size()) {
			paths.addAll(FileDTOUtils.getChildrenPathsList(this.getFilesByPathAndExtention(paths.get(i), "directory")));
			i++;
		}
		return paths;
	}	
	
	private FileDTO saveDTO(FileDTO dto) throws ServiceException {
		FileEntity entity = mapper.dtoToEntity(dto);
		try {
			repository.save(entity);
		} catch(Exception ex) {
			logger.error(ExceptionUtils.stackTraceToString(ex));
			throw new ServiceException(ex.getMessage(), ex);
		}
		return mapper.entityToDTO(entity);
		
	}

	@Override
	public FileDTO saveFile(AbstractDriver file) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FileDTO createFile(AbstractDriver file) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FileDTO createOrCheckFile(AbstractDriver dto) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}


	
}

