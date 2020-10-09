package com.fl.tools.infr.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class InfrConfiguration {

	@Value("${ftool.infr.RootDir}")
	private String rootDir;

	public String getRootDir() {
		return rootDir;
	}
}
