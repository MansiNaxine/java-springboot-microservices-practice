package com.example.demo.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.pojo.Hospital;

@Repository
public class HospitalRepository {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public Hospital findHospitalById(int hospitalId)
	{
		Hospital hospital=null;
		String query="select * from hospital where hospital_registration_id=?";
		try {
			hospital=jdbcTemplate.queryForObject(query, (rs,rowNum)->{
				
									Hospital h=new Hospital();
									h.setHospital_registration_id(rs.getInt(1));
									h.setAddress(rs.getString(2));
									h.setHospital_name(rs.getString(3));
									
									return h;
			},hospitalId);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return hospital;
		
	}
	
	
	public List<Integer> findDoctorIds(int hospitalId)
	{
		String query="select doctorId from hospital h inner join " +
					 "doctor_hospital_mapping d ON h.hospital_registration_id=d.hospitalId where h.hospital_registration_id=?";
	
		
		return jdbcTemplate.queryForList(query, Integer.class, hospitalId);
		
	}

}
