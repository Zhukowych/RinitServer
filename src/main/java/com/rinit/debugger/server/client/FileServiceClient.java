package com.rinit.debugger.server.client;

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
	public FileDTO createOrCheckFile(FileDTO dto) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isFileExists(FileDTO dto) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<String> getAllChildrenDirs(String baseDir, String extention) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FileDTO> getFilesByParentPathExtention(String parentPath, String extention) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
