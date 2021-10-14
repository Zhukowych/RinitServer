package com.rinit.debugger.server.test;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ConfigurationControllerTest {
	
	@LocalServerPort
	private int port;
	
	public String rinitServiceHost = "http://localhost:%d/init_config/";

	
	@Test
	public void createMainFileStructureTest() throws Exception {
		String host = String.format(rinitServiceHost, port);
		RestTemplate template = new RestTemplate();
		System.out.println(template.getForObject(host, Class.class));
	}
}
