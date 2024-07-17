package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.env.Environment;
import com.example.demo.feign.Hospital_Doctor_Feign;
import com.example.demo.pojo.Doctor;
import com.example.demo.pojo.Hospital;
import com.example.demo.pojo.MyToken;
import com.example.demo.repository.HospitalRepository;
import org.springframework.util.MultiValueMap;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

@RestController
public class FindDoctorsInHospitalController {

	@Autowired
	private HospitalRepository repo;
	
	@Autowired
	private Hospital_Doctor_Feign feign_client;
	
	@GetMapping("/hospitals/{hospitalId}")
	public ResponseEntity<Hospital> findAllDoctorsInHospital(@PathVariable int hospitalId)
	{
		List<Doctor> doctors=new ArrayList<>();
		Hospital hospital=repo.findHospitalById(hospitalId);
		
		if(hospital!=null)
		{
			List<Integer> doctorId=repo.findDoctorIds(hospitalId);
			RestTemplate restTemplate=new RestTemplate();
			for(int i=0;i<doctorId.size();i++)
			{
				ResponseEntity<Doctor> entity=restTemplate.getForEntity("http://localhost:8010/doctors/{doctorId}", Doctor.class,doctorId.get(i));
				
				if(entity.getStatusCode().equals(HttpStatus.OK))
				{
					doctors.add(entity.getBody());
				}
			}
			
			hospital.setDoctors(doctors);
			
			return new ResponseEntity<Hospital>(hospital,HttpStatus.OK);
		}
		
		return new ResponseEntity<Hospital>(HttpStatus.NO_CONTENT);
	}

	@Autowired
	RestTemplate restTemplate1;
	
	@GetMapping("/hospitals_balanced/{hospitalId}")
	public ResponseEntity<Hospital> findAllDoctorsInHospital_balanced(@PathVariable int hospitalId)
	{
		List<Doctor> doctors=new ArrayList<>();
		Hospital hospital=repo.findHospitalById(hospitalId);
		
		if(hospital!=null)
		{
			List<Integer> doctorId=repo.findDoctorIds(hospitalId);
			for(int i=0;i<doctorId.size();i++)
			{
				ResponseEntity<Doctor> entity=restTemplate1.getForEntity("http://DoctorFind-By-DoctorId-service/doctors/{doctorId}", Doctor.class,doctorId.get(i));
				
				if(entity.getStatusCode().equals(HttpStatus.OK))
				{
					doctors.add(entity.getBody());
				}
			}
			
			hospital.setDoctors(doctors);
			
			return new ResponseEntity<Hospital>(hospital,HttpStatus.OK);
		}
		
		return new ResponseEntity<Hospital>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/hospitals_balanced_gateway/{hospitalId}")
	public ResponseEntity<Hospital> findAllDoctorsInHospital_balanced_gateway(@PathVariable int hospitalId,
			@RequestHeader(name = "sort", defaultValue = "all") String sort)
	{
		List<Doctor> doctors=new ArrayList<>();
		Hospital hospital=repo.findHospitalById(hospitalId);
		
		if(hospital!=null)
		{
			List<Integer> doctorId=repo.findDoctorIds(hospitalId);
			for(int i=0;i<doctorId.size();i++)
			{
				ResponseEntity<Doctor> entity=restTemplate1.getForEntity("http://DoctorFind-By-DoctorId-service/doctors/{doctorId}", Doctor.class,doctorId.get(i));
				
				if(entity.getStatusCode().equals(HttpStatus.OK))
				{
					doctors.add(entity.getBody());
				}
			}
			
			hospital.setDoctors(doctors);
			
			return new ResponseEntity<Hospital>(hospital,HttpStatus.OK);
		}
		
		return new ResponseEntity<Hospital>(HttpStatus.NO_CONTENT);
	}
	
	@CircuitBreaker(name="circuit-breaker-for-doctor",fallbackMethod="getDoctorFallback")
	@GetMapping("/hospitals-circuit-breaker/{hospitalId}")
	public ResponseEntity<Hospital> findAllDoctorsInHospital_circuitBreaker(@PathVariable int hospitalId)
	{
		List<Doctor> doctors=new ArrayList<>();
		Hospital hospital =repo.findHospitalById(hospitalId);
		if(hospital!=null)
		{
			List<Integer> doctor_ids=repo.findDoctorIds(hospitalId);
			for(int i=0;i<doctor_ids.size();i++)
			{
				ResponseEntity<Doctor> entity=restTemplate1.getForEntity("http://DoctorFind-By-DoctorId-service/doctors/{doctorId}", Doctor.class,doctor_ids.get(i));
				if(entity.getStatusCode().equals(HttpStatus.OK))
				{
					doctors.add(entity.getBody());
				}
			}
			
			hospital.setDoctors(doctors);
			return new ResponseEntity<Hospital>(hospital,HttpStatus.OK);
		}
		
		return new ResponseEntity<Hospital>(hospital,HttpStatus.NO_CONTENT);
	}
	
	@Retry(name="retry-for-doctor",fallbackMethod="getDoctorFallback")
	@GetMapping("/hospitals-retry/{hospitalId}")
	public ResponseEntity<Hospital> findAllDoctorsInHospital_retry(@PathVariable int hospitalId)
	{
		List<Doctor> doctors=new ArrayList<>();
		Hospital hospital =repo.findHospitalById(hospitalId);
		if(hospital!=null)
		{
			List<Integer> doctor_ids=repo.findDoctorIds(hospitalId);
			for(int i=0;i<doctor_ids.size();i++)
			{
				ResponseEntity<Doctor> entity=restTemplate1.getForEntity("http://DoctorFind-By-DoctorId-service/doctors/{doctorId}", Doctor.class,doctor_ids.get(i));
				if(entity.getStatusCode().equals(HttpStatus.OK))
				{
					doctors.add(entity.getBody());
				}
			}
			
			hospital.setDoctors(doctors);
			return new ResponseEntity<Hospital>(hospital,HttpStatus.OK);
		}
		
		return new ResponseEntity<Hospital>(hospital,HttpStatus.NO_CONTENT);
	}
	
	
	//TO RETURN SOME LOGICAL VALUE
	
	public ResponseEntity<Hospital> getDoctorFallback(int hospitalId, Exception e)
	{
		Hospital hospital=repo.findHospitalById(hospitalId);
		if(hospital!=null)
		{
			return new ResponseEntity<Hospital>(hospital,HttpStatus.OK);
		}
		
		return new ResponseEntity<Hospital>(hospital,HttpStatus.NO_CONTENT);
	}
	
	
	
	@RateLimiter(name="rate-limiter-for-doctor",fallbackMethod="getDoctorFallback_rateLimiter")
	@GetMapping("/hospitals-rate-limiter/{hospitalId}")
	public ResponseEntity<Hospital> findAllDoctorsInHospital_rate_limiter(@PathVariable int hospitalId)
	{
		List<Doctor> doctors=new ArrayList<>();
		Hospital hospital =repo.findHospitalById(hospitalId);
		if(hospital!=null)
		{
			List<Integer> doctor_ids=repo.findDoctorIds(hospitalId);
			for(int i=0;i<doctor_ids.size();i++)
			{
				ResponseEntity<Doctor> entity=restTemplate1.getForEntity("http://DoctorFind-By-DoctorId-service/doctors/{doctorId}", Doctor.class,doctor_ids.get(i));
				if(entity.getStatusCode().equals(HttpStatus.OK))
				{
					doctors.add(entity.getBody());
				}
			}
			
			hospital.setDoctors(doctors);
			return new ResponseEntity<Hospital>(hospital,HttpStatus.OK);
		}
		
		return new ResponseEntity<Hospital>(hospital,HttpStatus.NO_CONTENT);
	}
	
	@RateLimiter(name="bulkhead-for-doctor",fallbackMethod="getDoctorFallback_rateLimiter")
	@GetMapping("/hospitals-bulkhead/{hospitalId}")
	public ResponseEntity<Hospital> findAllDoctorsInHospital_bulkhead(@PathVariable int hospitalId)
	{
		List<Doctor> doctors=new ArrayList<>();
		Hospital hospital =repo.findHospitalById(hospitalId);
		if(hospital!=null)
		{
			List<Integer> doctor_ids=repo.findDoctorIds(hospitalId);
			for(int i=0;i<doctor_ids.size();i++)
			{
				ResponseEntity<Doctor> entity=restTemplate1.getForEntity("http://DoctorFind-By-DoctorId-service/doctors/{doctorId}", Doctor.class,doctor_ids.get(i));
				if(entity.getStatusCode().equals(HttpStatus.OK))
				{
					doctors.add(entity.getBody());
				}
			}
			
			hospital.setDoctors(doctors);
			return new ResponseEntity<Hospital>(hospital,HttpStatus.OK);
		}
		
		return new ResponseEntity<Hospital>(hospital,HttpStatus.NO_CONTENT);
	}
	
	
	public ResponseEntity<Hospital> getDoctorFallback_rateLimiter(int hospitalId, Exception e)
	{
		
		System.out.println(e.getMessage());
		return new ResponseEntity<Hospital>(HttpStatus.TOO_MANY_REQUESTS);
	}
	
	@Autowired
	Environment env;
	
	@GetMapping("/hospitals_feign/{hospitalId}")
	public ResponseEntity<Hospital> findAllDoctorsInHospital_feign(@PathVariable int hospitalId)
	{
		List<Doctor> doctors=new ArrayList<>();
		Hospital hospital=repo.findHospitalById(hospitalId);
		
		if(hospital!=null)
		{
			List<Integer> doctorIds=repo.findDoctorIds(hospitalId);
			
			for(int i=0;i<doctorIds.size();i++)
			{
				ResponseEntity<Doctor> entity=feign_client.searchDoctorById(doctorIds.get(i));
				
				if(entity.getStatusCode().equals(HttpStatus.OK))
				{
					doctors.add(entity.getBody());
					System.out.println(env.getProperty("loadbalancer.client.name"));
				}
			}
			
			hospital.setDoctors(doctors);
			
			return new ResponseEntity<Hospital>(hospital,HttpStatus.OK);
		}
		return new ResponseEntity<Hospital>(HttpStatus.NO_CONTENT);
	}
	
	@TimeLimiter(name="timelimiter-for-doctor",fallbackMethod="getDoctorFallback_timelimiter")
	@GetMapping("/hospitals-timelimiter/{hospitalId}")
	CompletableFuture<ResponseEntity<Hospital>> findAllDoctorsInHospitals_timelimiter(@PathVariable int hospitalId)
	{
		return CompletableFuture.supplyAsync(()->{
			return this.getDetails(hospitalId);
		});
	}

	private ResponseEntity<Hospital> getDetails(int hospitalId) {
		System.out.println("Method called");
		List<Doctor> doctors=new ArrayList<>();
		Hospital hospital =repo.findHospitalById(hospitalId);
		if(hospital!=null)
		{
			List<Integer> doctor_ids=repo.findDoctorIds(hospitalId);
			for(int i=0;i<doctor_ids.size();i++)
			{
				ResponseEntity<Doctor> entity=restTemplate1.getForEntity("http://DoctorFind-By-DoctorId-service/doctors/{doctorId}", Doctor.class,doctor_ids.get(i));
				if(entity.getStatusCode().equals(HttpStatus.OK))
				{
					doctors.add(entity.getBody());
				}
			}
			
			hospital.setDoctors(doctors);
			return new ResponseEntity<Hospital>(hospital,HttpStatus.OK);
		}
		
		return new ResponseEntity<Hospital>(hospital,HttpStatus.NO_CONTENT);
	}
	
	public CompletableFuture<ResponseEntity<Hospital>> getDoctorFallback_timelimiter(int hospitalId, Exception e)
	{
		System.out.println(e.getMessage());
		return CompletableFuture.supplyAsync(()-> new ResponseEntity<Hospital>(HttpStatus.REQUEST_TIMEOUT));
	}
	
	/////Security handling Method
	
	@GetMapping("/hospitals-balanced-gateway-security/{hospitalId}")
	ResponseEntity<Hospital> findAllDoctorsInHospitals_loadBalanced_Gateway_Security(@PathVariable int hospitalId,
			@RequestHeader(name="sort", defaultValue="all")String sort)
	{
		List<Doctor> doctors=new ArrayList<>();
		Hospital hospital=repo.findHospitalById(hospitalId);
		if(hospital!=null)
		{
			
		   //Step 1: set the clientId,secret,username,password,grant_type etc to obtain the token
			MultiValueMap<String,String> map=new LinkedMultiValueMap<String,String>();
			map.add("username", "client1");
			map.add("password", "client1");
			map.add("grant_type", "client_credentials");
			map.add("client_secret", "iPfVUj8OZQPQcHdVyCjr594IFJ8H9wTv");
			map.add("client_id", "microservice_client");
			map.add("scope", "openid");
			
			HttpHeaders headers=new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			HttpEntity<MultiValueMap<String,String>> requestBodyFormUrlEncoded=new HttpEntity<>(map,headers);
			
			String access_token_url="http://localhost:8080/realms/security_demo/protocol/openid-connect/token";
			
			//Step 2: Use Rest Template to obtain the token from keycloak			)
			RestTemplate restTemplate=new RestTemplate();
			ResponseEntity<MyToken> response_token=restTemplate.exchange(access_token_url,
					   													HttpMethod.POST,
					   													requestBodyFormUrlEncoded,
					   													MyToken.class);
			
			
			//Step 3: Set the token as Bearer token to the header
			HttpHeaders headers_token=new HttpHeaders();
			headers_token.setBearerAuth(response_token.getBody().getAccess_token());
			HttpEntity<MultiValueMap<String,String>> request_doctor_header_entity=new HttpEntity<>(headers_token);
			
			List<Integer> doctor_ids=repo.findDoctorIds(hospitalId);
			System.out.println(doctor_ids.size());
			
			for(int i=0;i<doctor_ids.size();i++)
			{
				
				//Step 4: access the resourcse via Gateway API along with headers with bearer token using exchange()
				
				ResponseEntity<Doctor> entity=restTemplate1.exchange("http://API-Gateway-Service/doctors/{doctorId}",
																	HttpMethod.GET,request_doctor_header_entity,
																	Doctor.class
																	,doctor_ids.get(i));
				
				if(entity.getStatusCode().equals(HttpStatus.OK))
				{
					doctors.add(entity.getBody());
				}
			}
							
		}
		
		hospital.setDoctors(doctors);
		return new ResponseEntity<Hospital>(hospital,HttpStatus.OK);
		
	}
	
	@Autowired
	Hospital_Doctor_Feign hospital_feign;
	
	//security code using feign client

	@GetMapping("/hospitals_feign_Security/{hospitalId}")
	public ResponseEntity<Hospital> findAllDoctorsInHospital_feignSecurity(@PathVariable int hospitalId)
	{
		List<Doctor> doctors=new ArrayList<>();
		Hospital hospital=repo.findHospitalById(hospitalId);
		
		if(hospital!=null)
		{
			 //Step 1: set the clientId,secret,username,password,grant_type etc to obtain the token
			MultiValueMap<String,String> map=new LinkedMultiValueMap<String,String>();
			map.add("username", "client1");
			map.add("password", "client1");
			map.add("grant_type", "client_credentials");
			map.add("client_secret", "iPfVUj8OZQPQcHdVyCjr594IFJ8H9wTv");
			map.add("client_id", "microservice_client");
			map.add("scope", "openid");
			
			HttpHeaders headers=new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			HttpEntity<MultiValueMap<String,String>> requestBodyFormUrlEncoded=new HttpEntity<>(map,headers);
			
			String access_token_url="http://localhost:8080/realms/security_demo/protocol/openid-connect/token";
			
			//Step 2: Use Rest Template to obtain the token from keycloak	
			RestTemplate restTemplate =new RestTemplate();
			ResponseEntity<MyToken> response_token=restTemplate.exchange(access_token_url,
																		 	HttpMethod.POST,
																		 	requestBodyFormUrlEncoded,
																		 	MyToken.class);
					
	
			List<Integer> doctorIds=repo.findDoctorIds(hospitalId);
			
			for(int i=0;i<doctorIds.size();i++)
			{
				ResponseEntity<Doctor> entity=feign_client.searchDoctorById(
						String.format("%s %s", "Bearer" ,response_token.getBody().getAccess_token()),
								     doctorIds.get(i));
				
				if(entity.getStatusCode().equals(HttpStatus.OK))
				{
					doctors.add(entity.getBody());
				}
			}
			
			hospital.setDoctors(doctors);
			
			return new ResponseEntity<Hospital>(hospital,HttpStatus.OK);
		}
		return new ResponseEntity<Hospital>(HttpStatus.NO_CONTENT);
	}
	
	
	
	@GetMapping("/hospitals_feign_interceptor/{hospitalId}")
	public ResponseEntity<Hospital> findAllDoctorsInHospital_feignInterceptor(@RequestHeader(value="authorization", required=true)String authoriation,
											@PathVariable int hospitalId)
	{
		List<Doctor> doctors=new ArrayList<>();
		Hospital hospital=repo.findHospitalById(hospitalId);
		
		if(hospital!=null)
		{
			List<Integer> doctorIds=repo.findDoctorIds(hospitalId);
			
			for(int i=0;i<doctorIds.size();i++)
			{
				ResponseEntity<Doctor> entity=feign_client.searchDoctorById(authoriation,doctorIds.get(i));
				
				if(entity.getStatusCode().equals(HttpStatus.OK))
				{
					doctors.add(entity.getBody());
					//System.out.println(env.getProperty("loadbalancer.client.name"));
				}
			}
			
			hospital.setDoctors(doctors);
			
			return new ResponseEntity<Hospital>(hospital,HttpStatus.OK);
		}
		return new ResponseEntity<Hospital>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/hospitals_feign_relay/{hospitalId}")
	public ResponseEntity<Hospital> findAllDoctorsInHospital_feignRelay(@PathVariable int hospitalId)
	{
		List<Doctor> doctors=new ArrayList<>();
		Hospital hospital=repo.findHospitalById(hospitalId);
		
		if(hospital!=null)
		{
			List<Integer> doctorIds=repo.findDoctorIds(hospitalId);
			
			for(int i=0;i<doctorIds.size();i++)
			{
				ResponseEntity<Doctor> entity=feign_client.searchDoctorById(doctorIds.get(i));
				
				if(entity.getStatusCode().equals(HttpStatus.OK))
				{
					doctors.add(entity.getBody());
					//System.out.println(env.getProperty("loadbalancer.client.name"));
				}
			}
			
			hospital.setDoctors(doctors);
			
			return new ResponseEntity<Hospital>(hospital,HttpStatus.OK);
		}
		return new ResponseEntity<Hospital>(HttpStatus.NO_CONTENT);
	}
}
