package org.oze.hospital.dao;

import java.util.Optional;

import org.oze.hospital.entities.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends JpaRepository<Staff, String> {

    Optional<Staff> findByUuid(String uuid);

    Optional<Staff> findByUsername(String username);
    
}
