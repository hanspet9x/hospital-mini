package org.oze.hospital.entities;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.oze.hospital.models.UserRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.bcrypt.BCrypt;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "uuid", updatable = false, nullable = false)
    private UUID uuid = UUID.randomUUID();

    private String name;
    private String username;
    private String password;

    
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @CreationTimestamp
    private Date registrationDate;

    public Staff(UserRequest request) {
        this.name = request.getName();
        this.username = request.getUsername();
        this.password = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());
    }
    

}
