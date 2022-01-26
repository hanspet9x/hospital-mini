package org.oze.hospital.services;

import java.util.Optional;

import org.oze.hospital.dao.StaffRepository;
import org.oze.hospital.entities.Staff;
import org.oze.hospital.models.UserRequest;
import org.oze.hospital.tools.Helper;
import org.oze.hospital.tools.Helper.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StaffService {
    private final StaffRepository userRepository;
    private final JwtUserDetailsService userDetailsService;
    
    public ResponseEntity<ResponseMessage> createStaff(UserRequest request) {
        try {
            Optional<Staff> exist = userRepository.findByUsername(request.getUsername());
            if (exist.isPresent()){
                return Helper.ConflictResponse("Username already exist");
            } else {
                //Continue
                Staff user = new Staff(request);
                userRepository.save(user);
                return Helper.HealthyServerResponse("Member created successfully");
            }

        } catch (Exception e) {
            return Helper.ServerResponse(e);
        }
    }

    public ResponseEntity<?> login(UserRequest request) {
        try {
            Optional<Staff> user = userRepository.findByUsername(request.getUsername());
            if (user.isPresent()) {
                // Check password
                if (BCrypt.checkpw(request.getPassword(), user.get().getPassword())) {
                    return new ResponseEntity<>(userDetailsService.createJwtToken(request.getUsername(), request.getPassword()), HttpStatus.OK);
                } else {
                    return Helper.ConflictResponse("Incorrect password");
                }
            } else {
                return Helper.ConflictResponse("Invalid username");
            }
        } catch (Exception e) {
            return Helper.ServerResponse(e);
        }
    }

    public ResponseEntity<ResponseMessage> updateProfile(UserRequest request) {
        try {
            Optional<Staff> user = userRepository.findByUsername(request.getUsername());
            if (user.isPresent()) {
                user.get().setName(request.getName());
                userRepository.save(user.get());
                return Helper.HealthyServerResponse("Profile Updated");
            } else {
                return Helper.ConflictResponse("User details not found");
            }
        } catch (Exception e) {
            return Helper.ServerResponse(e);
        }
    }
}
