package org.oze.hospital.dao;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.oze.hospital.entities.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {

    List<Patient> findByLastVisitDateGreaterThan(Date twoYearsAgo);
    Page<Patient> findByLastVisitDateGreaterThan(Date twoYearsAgo, Pageable pageable);

    Optional<Patient> findById(int patientId);

    List<Patient> findByLastVisitDateBetween(String startDate, String endDate);
    
    List<Patient> findByLastVisitDateBetween(Date startDate, Date endDate);

    List<Patient> findByLastVisitDateBetweenAndIdIn(Date date, Date date2, List<Integer> patientIds);
    
}
