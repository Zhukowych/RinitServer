package com.rinit.debugger.server.dev.core;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import com.rinit.debugger.server.file.bin.AbstractBin;
import com.rinit.debugger.server.utils.CollectionUtils;

public class DevBins {
	
	private Map<String, Class<? extends AbstractBin>> devBins = new HashMap<String, Class<? extends AbstractBin>>();
	
	public DevBins() {
		this.createDevBins();
	}
	
	
	private void createDevBins() {
		Reflections reflections = new Reflections("com.rinit.debugger.server.dev.usr.bin", new SubTypesScanner(false));
		for(Class<? extends DevBin> devBinClass : reflections.getSubTypesOf(DevBin.class)) {
			DevBin devBin = this.createDevBin(devBinClass);
			this.devBins.put(devBin.getName(), devBin.getBinClass());
		}
	}

	public List<String> getBinsNames(){
		return CollectionUtils.setToList(this.devBins.keySet());
	}
	
	public Class<? extends AbstractBin> getBinClassByName(String name) {
		return this.devBins.get(name);
	}
	
	private DevBin createDevBin(Class<? extends DevBin> devBinClass) {
		try {
			return devBinClass.getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
