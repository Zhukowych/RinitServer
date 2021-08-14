package com.rinit.debugger.server.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rinit.debugger.server.dto.FileDTO;
import com.rinit.debugger.server.dto.FileDTOMapper;
import com.rinit.debugger.server.entity.FileEntity;
import com.rinit.debugger.server.exception.ServiceException;
import com.rinit.debugger.server.repository.FileRepository;
import com.rinit.debugger.server.services.interfaces.IFileService;
import com.rinit.debugger.server.utils.ExceptionUtils;



@Service
public class FileService implements IFileService {

	@Autowired
	private FileRepository repository;
	
	@Autowired
	private FileDTOMapper mapper;
	
	private static final Logger logger = LoggerFactory.getLogger(FileService.class);

	@Override
	public FileDTO createFile(FileDTO fileDTO) throws ServiceException {
		if(this.isFileExists(fileDTO)) {
			throw new ServiceException(String.format("File with name %s already exists", fileDTO.getName()));
		}
		
		FileEntity entity = mapper.dtoToEntity(fileDTO);
		try {
			repository.save(entity);
		} catch(Exception ex) {
			logger.error(ExceptionUtils.stackTraceToString(ex));
			throw new ServiceException(ex.getMessage(), ex);
		}
		return mapper.entityToDTO(entity);
	}
	
	@Override
	public FileDTO createOrCheckFile(FileDTO dto) throws ServiceException {
		if (!this.isFileExists(dto)) {
			return this.createFile(dto);
		}
		return dto;
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
	public boolean isFileExists(FileDTO dto) {
		if( this.repository.getFilesByNameExtentionPath(dto.getName(), dto.getExtention(), dto.getPath()).size() == 0 )
			return false;
		else
			return true;
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
	
}

