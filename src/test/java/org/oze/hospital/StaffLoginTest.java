package org.oze.hospital;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.oze.hospital.controllers.StaffController;
import org.oze.hospital.models.UserRequest;
import org.oze.hospital.services.StaffService;
import org.oze.hospital.tools.Helper;
import org.oze.hospital.tools.Helper.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(StaffController.class)
public class StaffLoginTest {
	
	@LocalServerPort
	static int port;
	
	@Autowired
	private TestRestTemplate rest;
	
	private static String register, login;
	private static String response;
	static HttpHeaders headers;
	static UserRequest request;
	static ObjectMapper mapper;
	
	@BeforeAll
	public static void initParameters () {
	   register = "http://127.0.0.1:"+port+"/hospital/staff/register"; 
		login = "http://127.0.0.1:"+port+"/hospital/staff/login";
		response = "Member created successfully";
		
		request = new UserRequest();
		request.setName("Akinlolu");
		request.setPassword("Test123");
		request.setUsername("Hanspet");
		
	    headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	     mapper  = new ObjectMapper();
	    
	}
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private StaffService service;
	
	@Test
	public void accessLogin () throws Exception {
		
		
		when(service.createStaff(request)).thenReturn((ResponseEntity<ResponseMessage>) Helper.HealthyServerResponse(response));
		this.mockMvc.perform(post(register, request)).andDo(print()).andExpect(status().isOk());



	}
}
