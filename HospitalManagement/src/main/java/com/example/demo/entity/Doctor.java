package com.example.demo.entity;

import java.util.Optional;

public class Doctor {
	
	private int doctorId;
	private String doctorName;
	private String specialization;
	
	public int getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	public String getSpecialization() {
		return specialization;
	}
	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}
	public Doctor(int doctorId, String doctorName, String string) {
		super();
		this.doctorId = doctorId;
		this.doctorName = doctorName;
		this.specialization = string;
	}
	
	public Doctor() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Doctor [doctorId=" + doctorId + ", doctorName=" + doctorName + ", specialization=" + specialization
				+ "]";
	}
	
	

}
