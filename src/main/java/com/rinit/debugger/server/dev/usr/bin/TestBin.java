package com.rinit.debugger.server.dev.usr.bin;

import com.rinit.debugger.server.file.bin.AbstractBin;

public class TestBin extends AbstractBin {

	@Override
	public void run() {
		System.out.println(123);
	}

	@Override
	public String getStarterMessage() {
		return "test_start_up_message";
	}

}
