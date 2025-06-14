package com.safehood.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    private String firstName;
    private String lastName;
    private String gender;
    private String address;
    private String phone;
    private String birthDate;
    private String profileImage;
    
    // Campos para barrios
    private String barrio1;
    private String barrio2;
    private String barrio3;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public enum Role {
        USER, ADMIN
    }
}
