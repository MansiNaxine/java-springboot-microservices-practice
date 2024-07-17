package com.example.demo.exception;

public class DoctorAlreadyExistsException extends RuntimeException {
	
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public DoctorAlreadyExistsException(String message) {
		super();
		this.message = message;
	}

	public DoctorAlreadyExistsException() {
		this("we have similar ecord");
		// TODO Auto-generated constructor stub
	}
	
	

}
