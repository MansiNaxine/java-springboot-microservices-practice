package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.DAO.DoctorDAO;
import com.example.demo.entity.Doctor;

@Repository
public class DoctorDAOImplementation implements DoctorDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public int addDoctor(Doctor doctor) {
		int added=0;
		String INSERT_DOCTOR="insert into Doctor values(?,?,?)";
		added=jdbcTemplate.update(INSERT_DOCTOR, doctor.getDoctorId(),doctor.getDoctorName(),doctor.getSpecialization());
		
		return added;
	}

	@Override
	public boolean updateDoctor(Doctor doctor) {
		boolean isUpdated=false;
		String UPDATE_DOCTOR="update Doctor set doctorName=?,specialization=? where doctorId=?";
		int updated=jdbcTemplate.update(UPDATE_DOCTOR, doctor.getDoctorId(),doctor.getDoctorName(),doctor.getSpecialization());
		if(updated>0)
		{
			isUpdated=true;
		}
		
		return isUpdated;
	}

	@Override
	public boolean updateSpecialization(int doctorId, String specialization) {
		boolean isUpdated=false;
		String UPDATE_specialization="update doctor set specialization=? where doctorId=?";
		int updated=jdbcTemplate.update(UPDATE_specialization,doctorId,specialization);
		if(updated>0)
		{
			isUpdated=true;
		}
		
		return isUpdated;
	}

	@Override
	public int deletedoctorById(int doctorId) {
		int deleted=0;
		String DELETE_DOCTOR="delete from Doctor where doctorId=?";
		deleted=jdbcTemplate.update(DELETE_DOCTOR,doctorId);
				
		return deleted;
	}

	@Override
	public Doctor findByDoctorId(int doctorId) {
		Doctor doctor= null;
		String FIND_DOCTOR="select * from Doctor where doctorId=?";
		doctor=jdbcTemplate.queryForObject(FIND_DOCTOR, (rs,rowNum) -> {
														Doctor d=new Doctor();
														d.setDoctorId(rs.getInt(1));
														d.setDoctorName(rs.getString(2));
														d.setSpecialization(rs.getString(3));
														
														return d;
														
		},doctorId);
		
		return doctor;
	}

	@Override
	public List<Doctor> findAllDoctor() {
		List<Doctor> doctors=new ArrayList<Doctor>();
		String FIND_DOCTORS="select * from Doctor";
		doctors=jdbcTemplate.query(FIND_DOCTORS, (rs,rowNum)->{
													Doctor d=new Doctor();
													d.setDoctorId(rs.getInt(1));
													d.setDoctorName(rs.getString(2));
													d.setSpecialization(rs.getString(3));
													
													return d;
		});
		
		return doctors;
	}

	@Override
	public List<Doctor> findAllDoctorsBySpecialization(String specialization) {
		List<Doctor> doctors=new ArrayList<>();
		String FIND_DOCTORS="select * from Doctor where specialization=?";
		doctors=jdbcTemplate.query(FIND_DOCTORS, (rs,rowNum)->{
			Doctor d=new Doctor();
			d.setDoctorId(rs.getInt(1));
			d.setDoctorName(rs.getString(2));
			d.setSpecialization(rs.getString(3));
			
			return d;
		},specialization);
		
		return doctors;
	}

}
