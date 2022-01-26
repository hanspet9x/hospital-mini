package org.oze.hospital.controllers;

import javax.servlet.http.HttpServletResponse;

import org.oze.hospital.models.DeleteRequest;
import org.oze.hospital.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/patient")
public class PatientController {
    @Autowired 
    private PatientService service;

    @GetMapping(value="/fetch-two-years-ago")
    public ResponseEntity<?> fetchTwoYearsAgoData(@RequestParam int page, int size ) {
        return service.fetch2YearsAgo(page, size);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> deletePatients(@RequestBody DeleteRequest request) {
        return service.deletePatients(request);
    }

    @GetMapping(value="/export/csv/{patientId}")
    public void exportPatientData(@PathVariable int patientId, HttpServletResponse response) throws Exception {
        service.exportPatientData(patientId, response);
    }
    
    
}
