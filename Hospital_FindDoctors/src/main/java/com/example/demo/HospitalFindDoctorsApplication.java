package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.example.demo.feign.CustomLoadBalancerConfiguration;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;


@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.example.demo.feign")
@LoadBalancerClient(name = "DoctorFind-By-DoctorId-service",configuration=CustomLoadBalancerConfiguration.class,value ="DoctorFind-By-DoctorId-service" )
public class HospitalFindDoctorsApplication {

	public static void main(String[] args) {
		SpringApplication.run(HospitalFindDoctorsApplication.class, args);
	}

}
