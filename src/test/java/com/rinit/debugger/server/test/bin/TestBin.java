package com.rinit.debugger.server.test.bin;

import com.rinit.debugger.server.file.bin.AbstractBin;

public class TestBin extends AbstractBin {

	@Override
	public void run() {
		System.out.println(12312);
	}

	@Override
	public String getStarterMessage() {
		return null;
	}

}
