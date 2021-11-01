package com.rinit.debugger.server.dev.core;

import com.rinit.debugger.server.file.bin.AbstractBin;

public interface DevBin {
	
	public String getName();
	public Class<? extends AbstractBin> getBinClass();
	
}
