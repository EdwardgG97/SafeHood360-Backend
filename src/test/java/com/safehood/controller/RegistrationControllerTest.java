package com.safehood.controller;

import com.safehood.dto.RegistrationDTO;
import com.safehood.service.RegistrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegistrationControllerTest {

    @Mock
    private RegistrationService registrationService;

    @InjectMocks
    private RegistrationController registrationController;

    private RegistrationDTO testRegistrationDTO;

    @BeforeEach
    void setUp() {
        testRegistrationDTO = new RegistrationDTO();
        testRegistrationDTO.setEmail("test@example.com");
        testRegistrationDTO.setFirstName("John");
        testRegistrationDTO.setLastName("Doe");
        testRegistrationDTO.setPassword("password123");
        testRegistrationDTO.setAddress("123 Main St");
        testRegistrationDTO.setPhone("1234567890");
        testRegistrationDTO.setBirthDate("1990-01-01");
        testRegistrationDTO.setGender("M");
        testRegistrationDTO.setBarrio1("Barrio 1");
        testRegistrationDTO.setBarrio2("Barrio 2");
        testRegistrationDTO.setBarrio3("Barrio 3");
        testRegistrationDTO.setProfileImage("image.jpg");
    }

    @Test
    void register_SuccessfulRegistration() {
        // Given
        doNothing().when(registrationService).register(any(RegistrationDTO.class));

        // When
        ResponseEntity<?> response = registrationController.register(testRegistrationDTO);

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Registro exitoso", response.getBody());
        verify(registrationService).register(testRegistrationDTO);
    }

    @Test
    void register_EmailAlreadyExists() {
        // Given
        doThrow(new RuntimeException("El correo electrónico ya está registrado"))
                .when(registrationService).register(any(RegistrationDTO.class));

        // When
        ResponseEntity<?> response = registrationController.register(testRegistrationDTO);

        // Then
        assertNotNull(response);
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("El correo electrónico ya está registrado", response.getBody());
        verify(registrationService).register(testRegistrationDTO);
    }
}
