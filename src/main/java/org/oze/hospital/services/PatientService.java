package org.oze.hospital.services;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.oze.hospital.dao.PatientRepository;
import org.oze.hospital.entities.Patient;
import org.oze.hospital.models.DeleteRequest;
import org.oze.hospital.tools.Helper;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public ResponseEntity<?> fetch2YearsAgo(int page, int size) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.YEAR, -2);
            Date twoYearsAgo = calendar.getTime();
            twoYearsAgo = dateFormat.parse(dateFormat.format(twoYearsAgo));
            List<Patient> patients = patientRepository.findByLastVisitDateGreaterThan(twoYearsAgo, PageRequest.of(page, size)).getContent();
            return Helper.HealthyServerResponse(patients);
        } catch (Exception e) {
            return Helper.ServerResponse(e);
        }
    }

    public void exportPatientData(int patientId, HttpServletResponse response) throws Exception {
        try {
            Optional<Patient> patient = patientRepository.findById(patientId);
            if (patient.isPresent()) {
                //Write CSV
                response.setContentType("text/csv");
                response.setCharacterEncoding(StandardCharsets.UTF_8.name());
                response.setHeader("Content-Disposition", "attachment; filename=patient.csv");
                // response.getWriter().print(Helper.anyToString(patient.get()));
                try(Writer writer = new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8)) {
                    writer.write("key,value\n");
                    writer.write("Name," + patient.get().getName()+"\n");
                    writer.write("Age," + patient.get().getAge()+"\n");
                    writer.write("Last Visit Date," + patient.get().getLastVisitDate()+"\n");
                    writer.flush();
                }
                catch (IOException e) {
                    throw new Exception(e.getMessage());
                }
            } else {
                throw new Exception("Invalid patient id");
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public ResponseEntity<?> deletePatients(DeleteRequest request) {
        try {
            List<Patient> patients = patientRepository.findByLastVisitDateBetween(dateFormat.parse(request.getStartDate()), dateFormat.parse(request.getEndDate()));
            patientRepository.deleteAll(patients);
            return Helper.HealthyServerResponse("Deleted successfully", patients.size() + " patients was deleted");
        } catch (Exception e) {
            return Helper.ServerResponse(e);
        }
    }
}
