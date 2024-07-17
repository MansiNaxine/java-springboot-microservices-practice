package com.example.demo.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@RestController
public class GlobalDoctorExceptionHandler {
	
	@ExceptionHandler(DoctorAlreadyExistsException.class)
	//@ResponseStatus(HttpStatus.NOT_MODIFIED)
	public ResponseEntity<Object> handleMyExeption(Exception ex, WebRequest rq)
	{
		ExceptionInfo exceptionInfo=new ExceptionInfo(new Date(), ex.getMessage(),rq.getDescription(false));
		
		return new ResponseEntity<Object>(exceptionInfo,HttpStatusCode.valueOf(400));
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleAllException(Exception ex, WebRequest rq)
	{
		ExceptionInfo exceptionInfo=new ExceptionInfo(new Date(), ex.getMessage(),rq.getDescription(false));
		
		return new ResponseEntity<Object>(exceptionInfo,HttpStatus.CONFLICT);
	}

}
