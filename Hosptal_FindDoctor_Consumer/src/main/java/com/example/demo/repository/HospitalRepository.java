package com.example.demo.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
		
		try {
			String FIND_QUERY="SELECT * FROM Hospital WHERE HOSPITAL_REGISTRATION_ID=?";
			hospital=jdbcTemplate.queryForObject(FIND_QUERY, (rs,rowNum)->{
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
		String QUERY="select doctorId from doctor_hospital_mapping d inner join " +
					 "hospital h ON d.hospitalId=h.hospital_registration_id where d.hospitalId=? ";
		
		return jdbcTemplate.queryForList(QUERY,Integer.class,hospitalId);
	}
}
