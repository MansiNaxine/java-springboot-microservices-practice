package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.pojo.Doctor;
import com.example.demo.pojo.Hospital;
import com.example.demo.repository.HospitalRepository;

@Configuration
@RestController
public class Find_Doctors_In_Hospital_Conrtollers {
	
	@Autowired
	HospitalRepository repo;
	
	
	List<Doctor> doctors_received=new ArrayList<>();
	
	@Bean
	Consumer<Doctor> readDoctors()
	{
		System.out.println("********************received doctor details*****************");
		
		return(doctor)->{
							doctors_received.add(doctor);
							System.out.println("Consumer Received : " + doctor);
						};
	}
		

	@GetMapping("/hospitals/{hospitalId}")
	public ResponseEntity<Hospital> findAllDoctorsByHospitalId(@PathVariable int hospitalId)
	{
		List<Doctor> doctors=new ArrayList<>();
		Hospital hospital=repo.findHospitalById(hospitalId);
		if(hospital!=null)
		{
			List<Integer> doctorIds=repo.findDoctorIds(hospitalId);
			for(int i=0;i<doctorIds.size();i++)
			{
				for(Doctor d:doctors_received)
				{
					if(d.getDoctorId()==doctorIds.get(i))
					{
						doctors.add(d);
					}
				}
				
			}
			
			hospital.setDoctors(doctors);
		
			return new ResponseEntity<Hospital>(hospital,HttpStatus.OK);
		}
		
		return new ResponseEntity<Hospital>(HttpStatus.NO_CONTENT);
	}
}
