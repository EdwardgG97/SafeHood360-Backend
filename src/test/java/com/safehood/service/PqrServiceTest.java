package com.safehood.service;

import com.safehood.dto.PqrDTO;
import com.safehood.model.Pqr;
import com.safehood.model.User;
import com.safehood.repository.PqrRepository;
import com.safehood.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PqrServiceTest {

    @Mock
    private PqrRepository pqrRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PqrService pqrService;

    private User testUser;
    private PqrDTO testPqrDTO;
    private Pqr testPqr;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setUsername("testuser");
        testUser.setEmail("test@example.com");

        testPqrDTO = new PqrDTO();
        testPqrDTO.setEmail("test@example.com");
        testPqrDTO.setMessage("Test message");

        testPqr = new Pqr();
        testPqr.setUser(testUser);
        testPqr.setEmail("test@example.com");
        testPqr.setMessage("Test message");
        testPqr.setStatus("Pendiente");
        testPqr.setCreatedAt(LocalDateTime.now());
        testPqr.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void createPqr_SuccessfulCreation() {
        // Given
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(testUser));
        when(pqrRepository.save(any(Pqr.class))).thenReturn(testPqr);

        // When
        pqrService.createPqr("testuser", testPqrDTO);

        // Then
        verify(pqrRepository, times(1)).save(any(Pqr.class));
        verify(userRepository, times(1)).findByUsername(anyString());
    }

    @Test
    void createPqr_UserNotFound_ThrowsException() {
        // Given
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            pqrService.createPqr("nonexistentuser", testPqrDTO);
        });
    }

    @Test
    void getUserPqrs_SuccessfulRetrieval() {
        // Given
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(testUser));
        when(pqrRepository.findByUser(any(User.class))).thenReturn(Arrays.asList(testPqr));

        // When
        List<PqrDTO> result = pqrService.getUserPqrs("testuser");

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testPqr.getEmail(), result.get(0).getEmail());
        assertEquals(testPqr.getMessage(), result.get(0).getMessage());
        assertEquals(testPqr.getStatus(), result.get(0).getStatus());
        assertEquals(testPqr.getCreatedAt(), result.get(0).getCreatedAt());
        assertEquals(testPqr.getUpdatedAt(), result.get(0).getUpdatedAt());
    }

    @Test
    void getUserPqrs_UserNotFound_ThrowsException() {
        // Given
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            pqrService.getUserPqrs("nonexistentuser");
        });
    }
}
