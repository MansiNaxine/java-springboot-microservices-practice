package com.example.demo.pojo;

import java.util.List;

public class Hospital {
	
	private int hospital_registration_id;
	private String address;
	private String hospital_name;
	
	private List<Doctor> doctors;

	public int getHospital_registration_id() {
		return hospital_registration_id;
	}

	public void setHospital_registration_id(int hospital_registration_id) {
		this.hospital_registration_id = hospital_registration_id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getHospital_name() {
		return hospital_name;
	}

	public void setHospital_name(String hospital_name) {
		this.hospital_name = hospital_name;
	}



	
	
	public Hospital(int hospital_registration_id, String address, String hospital_name, List<Doctor> doctors) {
		super();
		this.hospital_registration_id = hospital_registration_id;
		this.address = address;
		this.hospital_name = hospital_name;
		this.doctors = doctors;
	}

	public List<Doctor> getDoctors() {
		return doctors;
	}

	public void setDoctors(List<Doctor> doctors) {
		this.doctors = doctors;
	}

	public Hospital() {
		
	}

	@Override
	public String toString() {
		return "Hospital [hospital_registration_id=" + hospital_registration_id + ", address=" + address
				+ ", hospital_name=" + hospital_name + ", doctors=" + doctors + "]";
	}

	

}
