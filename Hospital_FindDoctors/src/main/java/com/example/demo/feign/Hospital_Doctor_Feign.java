package com.example.demo.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.example.demo.pojo.Doctor;

//@FeignClient(name="DoctorFind-By-DoctorId",url="http://localhost:8010")

@FeignClient(name="API-Gateway-Service",fallback=FeignClient_FallBack.class)
public interface Hospital_Doctor_Feign {
	
	@GetMapping("/doctors/{doctorId}")
	ResponseEntity<Doctor> searchDoctorById(@PathVariable int doctorId);
	
	@GetMapping("/doctors/{doctorId}")
	ResponseEntity<Doctor> searchDoctorById(@RequestHeader(value="Authorization",required=true)String authorization,@PathVariable int doctorId);
}
