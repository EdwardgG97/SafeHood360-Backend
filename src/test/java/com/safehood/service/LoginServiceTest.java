package com.safehood.service;

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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LoginService loginService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setUsername("testuser");
        testUser.setPassword("password123");
    }

    @Test
    void testValidLogin() {
        // Arrange
        when(userRepository.findByUsername(anyString()))
                .thenReturn(Optional.of(testUser));

        // Act
        User result = loginService.validateLogin("testuser", "password123");

        // Assert
        assertNotNull(result);
        assertEquals(testUser, result);
    }

    @Test
    void testInvalidUsername() {
        // Arrange
        when(userRepository.findByUsername(anyString()))
                .thenReturn(Optional.empty());

        // Act
        User result = loginService.validateLogin("nonexistent", "password123");

        // Assert
        assertNull(result);
    }

    @Test
    void testInvalidPassword() {
        // Arrange
        when(userRepository.findByUsername(anyString()))
                .thenReturn(Optional.of(testUser));

        // Act
        User result = loginService.validateLogin("testuser", "wrongpassword");

        // Assert
        assertNull(result);
    }

    @Test
    void testNullPassword() {
        // Arrange
        when(userRepository.findByUsername(anyString()))
                .thenReturn(Optional.of(testUser));

        // Act & Assert
        assertNull(loginService.validateLogin("testuser", null));
    }
}
