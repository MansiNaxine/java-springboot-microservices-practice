package com.example.demo.endpoint;

//import java.io.IOException;
//import java.net.MalformedURLException;
//import java.net.Socket;
//import java.net.UnknownHostException;
//
//import org.springframework.boot.actuate.health.Health;
//import org.springframework.boot.actuate.health.HealthIndicator;
//import org.springframework.stereotype.Component;

//@Component
//public class CustomHealthIndicator implements HealthIndicator {
//	
//	private static final String URL="http://localhost";
//
//	@Override
//	public Health health() {
//		
//		try {
//			@SuppressWarnings("deprecation")
//			Socket socket=new Socket(new java.net.URL(URL).getHost(),8761);
//		} catch (UnknownHostException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (Exception e) {
//			System.out.println("Failed to connect to: "+URL);
//			return Health.down().withDetail("Eureka Connection is down", e.getMessage()).build();
//		} 
//		return Health.up().build();
//	}
//	
//	
//
//	
//}
