package com.example.demo.DAO;

import java.util.List;

import com.example.demo.entity.Doctor;

public interface DoctorDAO {

	public int addDoctor(Doctor doctor);
	public boolean updateDoctor(Doctor doctor);
	public boolean updateSpecialization(int doctorId, String specialization);
	public int deletedoctorById(int doctorId);
	public Doctor findByDoctorId(int doctorId);
	public List<Doctor> findAllDoctor();
	public List<Doctor> findAllDoctorsBySpecialization(String specialization);

}
