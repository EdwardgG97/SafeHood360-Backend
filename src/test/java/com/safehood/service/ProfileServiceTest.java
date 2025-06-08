package com.safehood.service;

import com.safehood.dto.ProfileDTO;
import com.safehood.model.User;
import com.safehood.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfileServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ProfileService profileService;

    private User testUser;
    private ProfileDTO testProfileDTO;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setUsername("testuser");
        testUser.setFirstName("John");
        testUser.setLastName("Doe");
        testUser.setGender("M");
        testUser.setAddress("123 Test St");
        testUser.setEmail("test@example.com");
        testUser.setPhone("1234567890");
        testUser.setBirthDate("1990-01-01");
        testUser.setProfileImage("profile.jpg");

        testProfileDTO = new ProfileDTO();
        testProfileDTO.setFirstName("Jane");
        testProfileDTO.setLastName("Smith");
        testProfileDTO.setGender("F");
        testProfileDTO.setAddress("456 Main St");
        testProfileDTO.setPhone("0987654321");
        testProfileDTO.setBirthDate("1995-01-01");
        testProfileDTO.setProfileImage("new_profile.jpg");
    }

    @Test
    void getProfile_ExistingUser_ReturnsProfile() {
        // Given
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(testUser));

        // When
        ProfileDTO result = profileService.getProfile("testuser");

        // Then
        assertNotNull(result);
        assertEquals(testUser.getFirstName(), result.getFirstName());
        assertEquals(testUser.getLastName(), result.getLastName());
        assertEquals(testUser.getGender(), result.getGender());
        assertEquals(testUser.getAddress(), result.getAddress());
        assertEquals(testUser.getEmail(), result.getEmail());
        assertEquals(testUser.getPhone(), result.getPhone());
        assertEquals(testUser.getBirthDate().toString(), result.getBirthDate());
        assertEquals(testUser.getProfileImage(), result.getProfileImage());
    }

    @Test
    void getProfile_NonExistingUser_ThrowsException() {
        // Given
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            profileService.getProfile("nonexistentuser");
        });
    }

    @Test
    void updateProfile_SuccessfulUpdate() {
        // Given
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // When
        profileService.updateProfile("testuser", testProfileDTO);

        // Then
        verify(userRepository, times(1)).save(any(User.class));
        
        // Verificar que los campos se actualizaron correctamente
        assertEquals(testProfileDTO.getFirstName(), testUser.getFirstName());
        assertEquals(testProfileDTO.getLastName(), testUser.getLastName());
        assertEquals(testProfileDTO.getGender(), testUser.getGender());
        assertEquals(testProfileDTO.getAddress(), testUser.getAddress());
        assertEquals(testProfileDTO.getPhone(), testUser.getPhone());
        assertEquals(testProfileDTO.getBirthDate(), testUser.getBirthDate().toString());
        assertEquals(testProfileDTO.getProfileImage(), testUser.getProfileImage());
    }

    @Test
    void updateProfile_NonExistingUser_ThrowsException() {
        // Given
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            profileService.updateProfile("nonexistentuser", testProfileDTO);
        });
    }
}
