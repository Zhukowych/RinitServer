package com.rinit.debugger.server.client;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.web.client.RestTemplate;

import com.rinit.debugger.server.controller.urls.BinControllerUrls;
import com.rinit.debugger.server.services.interfaces.IBinService;

public class BinServiceClient implements IBinService {

	private String serviceHost;
	private RestTemplate template = new RestTemplate();
	
	public BinServiceClient(String serviceHost) {
		this.serviceHost = serviceHost;
	}
	
	@Override
	public void autodiscoverBins() {
		this.template.getForObject(this.serviceHost + BinControllerUrls.AUTODISCONER_BINS, String.class);
	}

	@Override
	public List<String> getAwailableBinsNames() {
		return Arrays.asList(this.template.getForObject(this.serviceHost+BinControllerUrls.GET_BIN_NAMES, String[].class));
	}

	@Override
	public Map<String, Class<?>> getBins() {
		return this.template.getForObject(this.serviceHost + BinControllerUrls.GET_BINIS, Map.class);
	}

}
