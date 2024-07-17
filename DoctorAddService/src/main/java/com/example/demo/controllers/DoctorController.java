package com.example.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.DoctorDAOImpl;
import com.example.demo.exceptions.DoctorAlreadyExistsException;
import com.example.demo.pojo.Doctor;

@RestController
public class DoctorController {
	
	@Autowired
	private DoctorDAOImpl doctorDao;
	
//	,consumes= {MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.APPLICATION_XML_VALUE}
	@PostMapping(path="/doctors")
	public ResponseEntity<Doctor> createNewDoctorRecord(@RequestBody Doctor doctor) throws DoctorAlreadyExistsException
	{
		Doctor d=null;
		int added=doctorDao.addDoctor(doctor);
		if(added==1)
		{
			return new ResponseEntity<Doctor>(doctor,HttpStatusCode.valueOf(201));
		}
		return new ResponseEntity<Doctor>(HttpStatusCode.valueOf(204));
	}
	
//	 consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE,produces= {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE}
	@PostMapping(path=("/doctors/form"))
	public Doctor createNewDoctorRecord_Form(@RequestBody Doctor doctor)
	{
		Doctor d=null;
		int added=doctorDao.addDoctor(doctor);
		if(added==1)
		{
			return doctor;
		}
		return d;
	}
	
//	@GetMapping("/doctors/{id}")
//	public Doctor findDoctorById(@PathVariable int id)
//	{
//		Doctor doctor=null;
//		doctor=doctorDao.findByDoctorId(id);
//		return doctor ;
//	}
//
//	@GetMapping("/doctors/specialization/{specialization}")
//	public List<Doctor> findByDoctorBySpecialization(@PathVariable("specialization") String doctor_specialization)
//	{
//		List<Doctor> doctors=doctorDao.findAllDoctorsBySpecialization(doctor_specialization);
//		return doctors;
//		}
//	
//	@GetMapping("/doctors")
//	public List<Doctor> findAllDoctors()
//	{
//		List<Doctor> doctors=doctorDao.findAllDoctor();
//		return doctors;
//	}
//	
//	@PutMapping("/doctors/{id}")
//	public Doctor modifyDoctorInfo(@PathVariable int id)
//	{
//		Doctor doctor=doctorDao.updateDoctor(null)
//		return doctor;
//	}
//	
//	
//	@PatchMapping("/doctors/{id}/{specialization}")
//	public Doctor modifyDoctorInfo(@PathVariable int id,@PathVariable String specialization)
//	{
//		Doctor doctor=null;
//		boolean isUpdated=false;
//		 isUpdated=doctorDao.updateSpecialization(id, specialization);
//		if(updated)
//		return doctor;
//	}
//	
//	@DeleteMapping("/doctors/{id}")
//	public Doctor deleteDoctorById(@PathVariable int id)
//	{
//	
//		Doctor doctor=doctorDao.deletedoctorById(id);
//		
//		return doctor;
//	}
//	
	
}
