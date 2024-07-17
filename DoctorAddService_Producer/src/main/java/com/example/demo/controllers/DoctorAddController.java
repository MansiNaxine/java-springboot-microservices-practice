package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.DoctorDAOImpl;
import com.example.demo.pojo.Doctor;

@RestController
public class DoctorAddController {
	
	@Autowired
	StreamBridge bridge;
	
	@Autowired
	private DoctorDAOImpl repo;
	
	@PostMapping("/doctors")
	public ResponseEntity<Doctor> addDoctor(@RequestBody Doctor doctor)
	{
		int added=repo.addDoctor(doctor);
		if(added==1)
		{
			boolean isPublished=bridge.send("doctorSupplier-out-0",MessageBuilder.withPayload(doctor).build());
			System.out.println("data published :"+ isPublished);
			return new ResponseEntity<Doctor>(doctor,HttpStatusCode.valueOf(201));
		}
		return new ResponseEntity<Doctor>(HttpStatusCode.valueOf(204));
	}

}
