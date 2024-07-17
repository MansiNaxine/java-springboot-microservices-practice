package com.example.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.pojo.Doctor;

@Repository
public class DoctorDAOImpl implements DoctorDAO {
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public int addDoctor(Doctor doctor) {
		
		int added=0;
		String INSERT_DOCTOR="insert into Doctor values(?,?,?)";
	    added=jdbcTemplate.update(INSERT_DOCTOR, doctor.getDoctorId(),doctor.getDoctorName(),doctor.getSpecialization());
		
		return added;
	}

}
