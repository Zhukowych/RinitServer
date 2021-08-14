package com.rinit.debugger.server.file.library;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ClassToLoadInfo {
	private String name;
	private String path;
}
