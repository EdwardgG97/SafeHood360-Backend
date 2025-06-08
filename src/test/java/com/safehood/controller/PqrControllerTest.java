package com.safehood.controller;

import com.safehood.dto.PqrDTO;
import com.safehood.service.PqrService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PqrControllerTest {

    @Mock
    private PqrService pqrService;

    @InjectMocks
    private PqrController pqrController;

    private PqrDTO testPqrDTO;
    private List<PqrDTO> testPqrList;

    @BeforeEach
    void setUp() {
        testPqrDTO = new PqrDTO();
        testPqrDTO.setEmail("test@example.com");
        testPqrDTO.setMessage("Test message");

        testPqrList = Arrays.asList(testPqrDTO);
    }

    @Test
    void createPqr_SuccessfulCreation() {
        // Given
        doNothing().when(pqrService).createPqr(anyString(), any(PqrDTO.class));

        // When
        ResponseEntity<?> response = pqrController.createPqr("testuser", testPqrDTO);

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("PQR creado exitosamente", response.getBody());
        verify(pqrService, times(1)).createPqr(anyString(), any(PqrDTO.class));
    }

    @Test
    void getUserPqrs_SuccessfulRetrieval() {
        // Given
        when(pqrService.getUserPqrs(anyString())).thenReturn(testPqrList);

        // When
        ResponseEntity<?> response = pqrController.getUserPqrs("testuser");

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof List);
        assertEquals(testPqrList, response.getBody());
        verify(pqrService, times(1)).getUserPqrs(anyString());
    }
}
