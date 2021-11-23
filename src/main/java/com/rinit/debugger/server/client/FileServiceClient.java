package com.rinit.debugger.server.client;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.rinit.debugger.server.controller.urls.FileControllerUrls;
import com.rinit.debugger.server.dto.FileDTO;
import com.rinit.debugger.server.exception.ServiceException;
import com.rinit.debugger.server.file.AbstractDriver;
import com.rinit.debugger.server.services.interfaces.IFileService;

public class FileServiceClient implements IFileService{
	
	private String serviceHost;
	private RestTemplate template = new RestTemplate();
	
	public FileServiceClient(String serviceHost) {
		this.serviceHost = serviceHost;
	}
		
	@Override
	public FileDTO saveFile(FileDTO file) throws ServiceException {
		HttpHeaders headers = new HttpHeaders();
	    headers.add("Accept", MediaType.APPLICATION_XML_VALUE);
	    headers.setContentType(MediaType.APPLICATION_XML);

		HttpEntity<FileDTO> request = new HttpEntity<>(file, headers);
		FileDTO savedFile = template.postForObject(this.serviceHost + FileControllerUrls.SAVE_FILE, request, FileDTO.class);
		return savedFile;
	}

	@Override
	public FileDTO createFile(FileDTO file) throws ServiceException {
		HttpHeaders headers = new HttpHeaders();
	    headers.add("Accept", MediaType.APPLICATION_XML_VALUE);
	    headers.setContentType(MediaType.APPLICATION_XML);

		HttpEntity<FileDTO> request = new HttpEntity<>(file, headers);
		FileDTO createdFile = template.postForObject(this.serviceHost + FileControllerUrls.CREATE_FILE_URL, request, FileDTO.class);
		return createdFile;
	}

	@Override
	public FileDTO saveFile(AbstractDriver file) throws ServiceException {
		file.setContent(file.buildContent());
		return this.saveFile(file.toDTO());
	}

	@Override
	public FileDTO createFile(AbstractDriver file) throws ServiceException {
		file.setContent(file.buildContent());
		return this.createFile(file.toDTO());
	}
	
	@Override
	public FileDTO createOrCheckFile(FileDTO dto) throws ServiceException {
		List<FileDTO> dtos =this.getFileByPathAndName(dto.getPath(), dto.getName());
		if (dtos.size() != 0)
			dto.setId(dtos.get(0).getId());	
		this.saveFile(dto);
		return dto;
	}	
		

	@Override
	public FileDTO createOrCheckFile(AbstractDriver dto) throws ServiceException {
		FileDTO file = dto.toDTO();
		file = this.createOrCheckFile(file);
		return file;
	}
		
	@Override
	public void copyFile(FileDTO dto, String destination) {
		HttpHeaders headers = new HttpHeaders();
	    headers.add("Accept", MediaType.APPLICATION_XML_VALUE);
	    headers.setContentType(MediaType.APPLICATION_XML);
	    UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(this.serviceHost + FileControllerUrls.COPY_FILE);
		HttpEntity<FileDTO> request = new HttpEntity<>(dto, headers);
		builder.queryParam("destination", destination);
		String ok = this.template.postForObject(builder.toUriString(), request, String.class);
	}

	@Override
	public void renMove(FileDTO dto, String destination) {
		HttpHeaders headers = new HttpHeaders();
	    headers.add("Accept", MediaType.APPLICATION_XML_VALUE);
	    headers.setContentType(MediaType.APPLICATION_XML);
	    UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(this.serviceHost + FileControllerUrls.REN_MOVE_FILE);
		HttpEntity<FileDTO> request = new HttpEntity<>(dto, headers);
		builder.queryParam("destination", destination);
		String ok = this.template.postForObject(builder.toUriString(), request, String.class);		
	}

	@Override
	public List<FileDTO> getFilesByPath(String path) {
		String url = this.serviceHost + FileControllerUrls.GET_FILES_URL;
		HttpHeaders headers = new HttpHeaders();
	    headers.add("Accept", MediaType.APPLICATION_XML_VALUE);
	    headers.setContentType(MediaType.APPLICATION_XML);
	    HttpEntity<?> request = new HttpEntity<>(headers);
	    UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
	    builder.queryParam("path", path);
	    List<FileDTO> files = new ArrayList<FileDTO>(Arrays.asList(this.template.getForObject(builder.toUriString(), FileDTO[].class)));
		return files;
	}

	@Override
	public List<FileDTO> getFilesByPathAndExtention(String path, String extention) {
		return null;
	}

	@Override
	public List<FileDTO> getFileByPathAndName(String path, String name) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(this.serviceHost + FileControllerUrls.GET_FILES_BY_PATH_NAME);
		builder.queryParam("path", path);
		builder.queryParam("name", name);
		List<FileDTO> files = new ArrayList<FileDTO>(Arrays.asList(this.template.getForObject(builder.toUriString(), FileDTO[].class)));
		return files;
	}
	
	@Override
	public void deleteFile(FileDTO dto) {
		HttpHeaders headers = new HttpHeaders();
	    headers.add("Accept", MediaType.APPLICATION_XML_VALUE);
	    headers.setContentType(MediaType.APPLICATION_XML);
		HttpEntity<FileDTO> request = new HttpEntity<>(dto, headers);
		template.postForObject(this.serviceHost + FileControllerUrls.DELETE_FILE, request, String.class);
	}

	@Override
	public void deleteAllChildrenOfPath(String path) throws ServiceException {
		String url = this.serviceHost + FileControllerUrls.DELETE_CHILDRENS;
	    UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
	    builder.queryParam("path", path);
	    this.template.getForObject(builder.toUriString(), String.class);		
	}

	@Override
	public boolean isFileExists(FileDTO dto) {
		List<FileDTO> files = this.getFileByPathAndName(dto.getPath(), dto.getName());
		if(files.size() != 0)
			return true;
		else
			return false;
	}

	@Override
	public List<String> getAllChildrenDirs(String baseDir, String extention) {
		return null;
	}

	@Override
	public List<FileDTO> getFilesByParentPathExtention(String parentPath, String extention) {
		List<FileDTO> result = new ArrayList<FileDTO>();
		for (FileDTO dto : this.getFilesByPath(parentPath)) {
			if (dto.getExtention().equals(extention))
				result.add(dto);
		}
		return result;
	}

}
