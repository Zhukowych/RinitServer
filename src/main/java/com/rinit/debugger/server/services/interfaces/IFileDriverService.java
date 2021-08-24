package com.rinit.debugger.server.services.interfaces;

import com.rinit.debugger.server.entity.IFileDriver;


public interface IFileDriverService {

	public void registerDriver(String extention, Class<IFileDriver> driver);
}
