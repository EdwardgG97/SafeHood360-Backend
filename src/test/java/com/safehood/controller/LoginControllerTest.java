package com.safehood.controller;

import com.safehood.dto.LoginDTO;
import com.safehood.dto.ResponseDTO;
import com.safehood.model.User;
import com.safehood.service.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest {

    @Mock
    private LoginService loginService;

    @InjectMocks
    private LoginController loginController;

    private LoginDTO validLoginDTO;
    private User testUser;

    @BeforeEach
    void setUp() {
        validLoginDTO = new LoginDTO();
        validLoginDTO.setUsername("testuser");
        validLoginDTO.setPassword("testpassword");

        testUser = new User();
        testUser.setUsername("testuser");
        testUser.setPassword("testpassword");
    }

    @Test
    void shouldReturnOkWhenLoginIsSuccessful() {
        // Arrange
        when(loginService.validateLogin(anyString(), anyString()))
            .thenReturn(testUser);

        // Act
        ResponseEntity<?> response = loginController.login(validLoginDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof ResponseDTO);
        ResponseDTO responseBody = (ResponseDTO) response.getBody();
        assertEquals("Login exitoso", responseBody.getMessage());
        assertTrue(responseBody.isSuccess());
    }

    @Test
    void shouldReturnBadRequestWhenFieldsAreEmpty() {
        // Arrange
        LoginDTO emptyDTO = new LoginDTO();

        // Act
        ResponseEntity<?> response = loginController.login(emptyDTO);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody() instanceof ResponseDTO);
        ResponseDTO responseBody = (ResponseDTO) response.getBody();
        assertEquals("Los campos de usuario y contraseña son requeridos", responseBody.getMessage());
        assertFalse(responseBody.isSuccess());
    }

    @Test
    void shouldReturnUnauthorizedWhenUserNotFound() {
        // Arrange
        LoginDTO invalidUserDTO = new LoginDTO();
        invalidUserDTO.setUsername("nonexistent");
        invalidUserDTO.setPassword("password");
        
        when(loginService.validateLogin(anyString(), anyString()))
            .thenReturn(null);

        // Act
        ResponseEntity<?> response = loginController.login(invalidUserDTO);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertTrue(response.getBody() instanceof ResponseDTO);
        ResponseDTO responseBody = (ResponseDTO) response.getBody();
        assertEquals("Credenciales inválidas", responseBody.getMessage());
        assertFalse(responseBody.isSuccess());
    }

    @Test
    void shouldReturnUnauthorizedWhenPasswordDoesNotMatch() {
        // Arrange
        LoginDTO invalidPasswordDTO = new LoginDTO();
        invalidPasswordDTO.setUsername("testuser");
        invalidPasswordDTO.setPassword("wrongpassword");
        
        when(loginService.validateLogin(anyString(), anyString()))
            .thenReturn(null);

        // Act
        ResponseEntity<?> response = loginController.login(invalidPasswordDTO);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertTrue(response.getBody() instanceof ResponseDTO);
        ResponseDTO responseBody = (ResponseDTO) response.getBody();
        assertEquals("Credenciales inválidas", responseBody.getMessage());
        assertFalse(responseBody.isSuccess());
    }

    @Test
    void shouldReturnBadRequestWhenFieldsAreNull() {
        // Arrange
        LoginDTO nullDTO = new LoginDTO();
        nullDTO.setUsername(null);
        nullDTO.setPassword(null);

        // Act
        ResponseEntity<?> response = loginController.login(nullDTO);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody() instanceof ResponseDTO);
        ResponseDTO responseBody = (ResponseDTO) response.getBody();
        assertEquals("Los campos de usuario y contraseña son requeridos", responseBody.getMessage());
        assertFalse(responseBody.isSuccess());
    }
}