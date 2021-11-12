package com.rinit.debugger.server.client;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.rinit.debugger.server.client.interfaces.IPhysicalFileServiceClient;
import com.rinit.debugger.server.controller.urls.FileControllerUrls;
import com.rinit.debugger.server.controller.urls.PhysicalFileControllerUrls;
import com.rinit.debugger.server.exception.ServiceException;
import com.rinit.debugger.server.file.pfille.PhysicalFileDriver;
import com.rinit.debugger.server.services.interfaces.IPhysicalFileService;

public class PhysicalFileServiceClient implements IPhysicalFileServiceClient {

	private String serviceHost;
	private RestTemplate template = new RestTemplate();
	
	public PhysicalFileServiceClient(String serviceHost) {
		this.serviceHost = serviceHost;
	}
	
	
	@Override
	public PhysicalFileDriver uploadFile(PhysicalFileDriver physicalFile, MultipartFile file) throws ServiceException {
		String url = this.serviceHost + FileControllerUrls.LOAD_PHYSICAL_FILE_URL;
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_XML));
	    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
	    MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
	    try {
			body.add("file", new MultipartInputStreamFileResource(file.getInputStream(), file.getOriginalFilename()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	    UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
	    builder.queryParam("name", physicalFile.getName());
	    
	    HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
	    PhysicalFileDriver response = null;
	    try {
	    	response = this.template.postForObject(builder.toUriString(), requestEntity, PhysicalFileDriver.class);
	    }catch(HttpClientErrorException ex) {
	    	throw new ServiceException(ex.getMessage());
	    }
	    return response;
	}

	@Override
	public PhysicalFileDriver downloadPhysicalFileByPath(String ppath) {
		String path = this.downloadPhysicalFileByPhysicalLocatin(ppath);
		PhysicalFileDriver pfile = new PhysicalFileDriver();
		pfile.setFilePath(path);
		return pfile;
	}

	@Override
	public InputStream getPfileByPhysicalPath(String ppath) throws ServiceException {
		return null;
	}

	private String downloadPhysicalFileByPhysicalLocatin(String ppath) {
	    UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(this.serviceHost + PhysicalFileControllerUrls.DOWNLOAD_FILE_BY_PHYSICAL_PATH);
	    builder.queryParam("ppath", ppath.replace("\\", "/"));

	    byte[] fileBytesBytes = this.template.getForObject(builder.toUriString(), byte[].class);

	    
		Path path = Paths.get(ppath);
		String fileName = path.getFileName().toString();
		
		InputStream stream = new ByteArrayInputStream(fileBytesBytes);
		File file = new File("jars/"+fileName).getAbsoluteFile();
		Path copyLocation = Paths.get(file.getParent() + File.separator + fileName);
		try {
			Files.copy(stream, copyLocation, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return copyLocation.toString();
	}
}

class MultipartInputStreamFileResource extends InputStreamResource {

    private final String filename;

    MultipartInputStreamFileResource(InputStream inputStream, String filename) {
        super(inputStream);
        this.filename = filename;
    }

    @Override
    public String getFilename() {
        return this.filename;
    }

    @Override
    public long contentLength() throws IOException {
        return -1; // we do not want to generally read the whole stream into memory ...
    }
}

