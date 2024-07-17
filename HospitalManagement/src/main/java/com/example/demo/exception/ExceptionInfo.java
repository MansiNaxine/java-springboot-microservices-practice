package com.example.demo.exception;

import java.util.Date;

public class ExceptionInfo {
	
	private Date timeInfo;
	private String message;
	private String details;
	public Date getTimeInfo() {
		return timeInfo;
	}
	public void setTimeInfo(Date timeInfo) {
		this.timeInfo = timeInfo;
	}
	public String getMessage() {
		return "record exists";
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public ExceptionInfo(Date timeInfo, String message, String details) {
		super();
		this.timeInfo = timeInfo;
		this.message = message;
		this.details = details;
	}
	@Override
	public String toString() {
		return "ExceptionInfo [timeInfo=" + timeInfo + ", message=" + message + ", details=" + details + "]";
	}
	public ExceptionInfo() {
		
	}
	
	
	

}
