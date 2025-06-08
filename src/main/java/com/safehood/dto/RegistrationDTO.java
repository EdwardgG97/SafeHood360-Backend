package com.safehood.dto;

import lombok.Data;

@Data
public class RegistrationDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String address;
    private String phone;
    private String birthDate;
    private String gender;
    private String barrio1;
    private String barrio2;
    private String barrio3;
    private String profileImage; // Base64 encoded image
}
