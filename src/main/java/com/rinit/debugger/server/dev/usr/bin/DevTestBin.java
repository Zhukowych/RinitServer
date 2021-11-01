package com.rinit.debugger.server.dev.usr.bin;

import com.rinit.debugger.server.dev.core.DevBin;
import com.rinit.debugger.server.file.bin.AbstractBin;

public class DevTestBin implements DevBin {

	@Override
	public String getName() {
		return "test_bin_dev";
	}

	@Override
	public Class<? extends AbstractBin> getBinClass() {
		return TestBin.class;
	}

}
