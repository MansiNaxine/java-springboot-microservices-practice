package com.example.demo.endpoint;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.DeleteOperation;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;


@Component
@Endpoint(id="system-endpoint")
public class MyCustomEndpoint {
	
	@Autowired
	SystemInfo systemInfo; 		
	
	@ReadOperation		
	public SystemInfo system()
	{
		return systemInfo;
		
	}
	
	@ReadOperation
	public String  readSystemInfoByName(@Selector String name)
	{
	String info=systemInfo.getSystemDetails().get(name);
	
		return info;
	}
	
	@WriteOperation
	public SystemInfo updateSystemInfo_statusOK(String name)
	{
		System.out.println(name);
		
		if(name.equals(("processors")));
		{
			System.out.println("adding extra info");
			Map<String, String> details= systemInfo.getSystemDetails();
			
			details.put(name, Runtime.getRuntime().availableProcessors()+"");
			
			systemInfo.setSystemDetails(details);
		}
		return systemInfo;
		
	}

	@DeleteOperation
	public void modifySystemInfo_queryParam(String name)
	{
		systemInfo.getSystemDetails().remove(name);
	}
}
