package com.example.demo.endpoint;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonAnyGetter;

@Component
public class SystemInfo {

	
	private Map<String,String> systemDetails;
	
	

	public SystemInfo() {
		
		systemDetails=new LinkedHashMap<>();
		
		String osName=System.getProperty("os.name");
		String osVersion=System.getProperty("os.version");
		String osArchitecture=System.getProperty("os.arch");
		
		systemDetails.put("os.name", osName);
		systemDetails.put("os.version", osVersion);
		systemDetails.put("os.arch", osArchitecture);
		
	}

	@JsonAnyGetter
	public Map<String, String> getSystemDetails() {
		return systemDetails;
	}

	public void setSystemDetails(Map<String, String> systemDetails) {
		this.systemDetails = systemDetails;
	}
	
	
}
