package org.oze.hospital.controllers;

import org.oze.hospital.models.UserRequest;
import org.oze.hospital.services.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping(path = "/staff")
public class StaffController {
	
	private final StaffService service;
	public StaffController(StaffService service) {
		this.service = service;
	}
	

    @PostMapping(value="/register")
    public ResponseEntity<?> createStaff(@RequestBody UserRequest request) {
        return service.createStaff(request);
    }

    @PostMapping(value="/login")
    public ResponseEntity<?> login(@RequestBody UserRequest request) {
        return service.login(request);
    }

    @PutMapping(value="/update")
    public ResponseEntity<?> updateProfile(@RequestBody UserRequest request) {
        return service.updateProfile(request);
    }
    
    
}
