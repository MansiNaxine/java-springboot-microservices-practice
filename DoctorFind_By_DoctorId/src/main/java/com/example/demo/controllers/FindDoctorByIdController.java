package com.example.demo.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.pojo.Doctor;
import com.example.demo.repository.DoctorRepository;

import io.micrometer.core.annotation.Timed;

@RestController
public class FindDoctorByIdController {

	@Autowired
	DoctorRepository repo;
	
	@Timed(value = "execution.time", description = "Time taken to return Doctor")
	@GetMapping(value="/doctors/{doctorId}")
	public ResponseEntity<Doctor> findDoctorById(@PathVariable int doctorId,@RequestHeader(name="sort",defaultValue="all") String sort)
	{
		Optional<Doctor> optional=repo.findById(doctorId);
		if(optional.isPresent())
		{
			Doctor doctor=optional.get();
			
			return new ResponseEntity<Doctor>(doctor,HttpStatus.OK);
		}
		
		return new ResponseEntity<Doctor>(HttpStatus.NO_CONTENT);
	}
}
