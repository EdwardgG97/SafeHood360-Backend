package com.safehood.controller;

import com.safehood.dto.ProfileDTO;
import com.safehood.service.ProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfileControllerTest {

    @Mock
    private ProfileService profileService;

    @InjectMocks
    private ProfileController profileController;

    private ProfileDTO testProfileDTO;

    @BeforeEach
    void setUp() {
        testProfileDTO = new ProfileDTO();
        testProfileDTO.setFirstName("John");
        testProfileDTO.setLastName("Doe");
        testProfileDTO.setGender("M");
        testProfileDTO.setAddress("123 Test St");
        testProfileDTO.setPhone("1234567890");
        testProfileDTO.setBirthDate("1990-01-01");
        testProfileDTO.setProfileImage("profile.jpg");
    }

    @Test
    void getProfile_SuccessfulRetrieval() {
        // Given
        when(profileService.getProfile(anyString())).thenReturn(testProfileDTO);

        // When
        ResponseEntity<ProfileDTO> response = profileController.getProfile("testuser");

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(testProfileDTO, response.getBody());
        verify(profileService, times(1)).getProfile(anyString());
    }

    @Test
    void getProfile_UserNotFound_ThrowsException() {
        // Given
        when(profileService.getProfile(anyString())).thenThrow(new RuntimeException("Usuario no encontrado"));

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            profileController.getProfile("nonexistentuser");
        });
    }

    @Test
    void updateProfile_SuccessfulUpdate() {
        // Given
        doNothing().when(profileService).updateProfile(anyString(), any(ProfileDTO.class));

        // When
        ResponseEntity<?> response = profileController.updateProfile("testuser", testProfileDTO);

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Perfil actualizado exitosamente", response.getBody());
        verify(profileService, times(1)).updateProfile(anyString(), any(ProfileDTO.class));
    }

    @Test
    void updateProfile_UserNotFound_ThrowsException() {
        // Given
        doThrow(new RuntimeException("Usuario no encontrado")).when(profileService).updateProfile(anyString(), any(ProfileDTO.class));

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            profileController.updateProfile("nonexistentuser", testProfileDTO);
        });
    }
}
