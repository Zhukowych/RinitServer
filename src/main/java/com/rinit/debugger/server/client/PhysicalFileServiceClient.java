package com.rinit.debugger.server.client;

import java.io.IOException;
import java.io.InputStream;
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

import com.rinit.debugger.server.controller.FileControllerUrls;
import com.rinit.debugger.server.exception.ServiceException;
import com.rinit.debugger.server.file.driver.PhysicalFileDriver;
import com.rinit.debugger.server.services.interfaces.IPhysicalFileService;

public class PhysicalFileServiceClient implements IPhysicalFileService {

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

