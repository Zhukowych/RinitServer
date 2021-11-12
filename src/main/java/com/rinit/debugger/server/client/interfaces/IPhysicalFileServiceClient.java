package com.rinit.debugger.server.client.interfaces;

import com.rinit.debugger.server.file.pfille.PhysicalFileDriver;
import com.rinit.debugger.server.services.interfaces.IPhysicalFileService;

public interface IPhysicalFileServiceClient extends IPhysicalFileService {

	public PhysicalFileDriver downloadPhysicalFileByPath(String ppath);
	
}
