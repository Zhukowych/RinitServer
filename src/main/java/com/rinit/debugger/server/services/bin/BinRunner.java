package com.rinit.debugger.server.services.bin;

import java.lang.reflect.InvocationTargetException;

import com.rinit.debugger.server.file.bin.AbstractBin;

public class BinRunner implements Runnable {

	private Class<AbstractBin> binClass;
	
	public BinRunner(Class<AbstractBin> binClass) {
		this.binClass = binClass;
	}
	
	@Override
	public void run() {
		AbstractBin bin = null;
		try {
			bin = this.binClass.getDeclaredConstructor().newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bin.run();
	}

}
