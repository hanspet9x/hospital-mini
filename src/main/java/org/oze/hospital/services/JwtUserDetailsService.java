package org.oze.hospital.services;

import java.util.ArrayList;
import java.util.Optional;

import org.oze.hospital.configs.JwtTokenUtil;
import org.oze.hospital.dao.StaffRepository;
import org.oze.hospital.entities.Staff;
import org.oze.hospital.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    StaffRepository userRepository;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    AuthenticationManager authenticationManager;

    public Response createJwtToken(String username, String password) throws Exception {
        authenticate(username, password);

        final UserDetails userDetails = loadUserByUsername(username);
        String newToken = jwtTokenUtil.generateToken(userDetails);
        return new Response(false, "Login successful", newToken);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Staff> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            return new org.springframework.security.core.userdetails.User(user.get().getUsername(), user.get().getPassword(), new ArrayList<>());
            
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);            
        }
    }
    
    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));            
        } catch (DisabledException e) {
            throw new Exception("User is disabled");
        }
        catch (BadCredentialsException ex) {
            throw new Exception("Invalid Credentials");
        }
    }

}
