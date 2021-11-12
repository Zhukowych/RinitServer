package com.rinit.debugger.server.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.rinit.debugger.server.controller.urls.BinControllerUrls;
import com.rinit.debugger.server.services.interfaces.IBinService;

@RestController
@Transactional
public class BinContoller {

	@Autowired
	private IBinService binService;

	@GetMapping(BinControllerUrls.AUTODISCONER_BINS)
	@ResponseBody
	public String autodiscoverBins() {
		this.binService.autodiscoverBins();
		return "ok";
	}
	
	@GetMapping(BinControllerUrls.GET_BIN_NAMES)
	@ResponseBody
	public List<String> getBinNames() {
		return this.binService.getAwailableBinsNames();
	}
		
	@GetMapping(BinControllerUrls.GET_BINIS)
	@ResponseBody
	public Map<String, Class<?>> getBin() {
		return this.binService.getBins();
	}
	
	
	
}
