package com.rinit.debugger.server.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.rinit.debugger.server.entity.FileEntity;

@Component
public class FileDTOMapper {

	public FileDTO entityToDTO(FileEntity entity) {
		FileDTO dto = FileDTO.builder()
						.id(entity.getId())
						.name(entity.getName())
						.extention(entity.getExtention())
						.path(entity.getPath())
						.position(entity.getPosition())
						.content(entity.getContent())
						.build();
		return dto;
	}
	
	public FileEntity dtoToEntity(FileDTO dto) {
		Long id = null;
		if (dto.getId() != null && dto.getId() != 0) {
			id = dto.getId();
		}
		FileEntity entity = FileEntity.builder()
						.id(id)
						.name(dto.getName())
						.extention(dto.getExtention())
						.path(dto.getPath())
						.position(dto.getPosition())
						.content(dto.getContent())
						.build();
		return entity;
	}
	
	public List<FileDTO> entityToDtoList(List<FileEntity> entities){
		List<FileDTO> dtos = new ArrayList<FileDTO>(entities.size());
		for(FileEntity entity : entities) {
			dtos.add(this.entityToDTO(entity));
		}
		return dtos;
	}
	
	public List<FileEntity> dtoToEntityList(List<FileDTO> dtos){
		List<FileEntity> entities = new ArrayList<FileEntity>(dtos.size());
		for(FileDTO dto : dtos) {
			entities.add(this.dtoToEntity(dto));
		}
		return entities;
	}
}
