package com.safehood.controller;

import com.safehood.dto.RegistrationDTO;
import com.safehood.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class RegistrationController {
    
    @Autowired
    private RegistrationService registrationService;
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationDTO registrationDTO) {
        try {
            registrationService.register(registrationDTO);
            return ResponseEntity.ok("Registro exitoso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
