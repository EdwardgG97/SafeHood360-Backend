package com.safehood.service;

import com.safehood.dto.RegistrationDTO;
import com.safehood.model.User;
import com.safehood.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RegistrationService registrationService;

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
        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.empty());

        // When
        registrationService.register(testRegistrationDTO);

        // Then
        verify(userRepository).findByEmail(testRegistrationDTO.getEmail());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void register_EmailAlreadyExists() {
        // Given
        User existingUser = new User();
        existingUser.setEmail(testRegistrationDTO.getEmail());
        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(existingUser));

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(testRegistrationDTO);
        });

        verify(userRepository).findByEmail(testRegistrationDTO.getEmail());
        verify(userRepository, never()).save(any(User.class));
    }
}
