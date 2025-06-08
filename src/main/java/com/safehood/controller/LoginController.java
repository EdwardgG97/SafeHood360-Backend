package com.safehood.controller;

import com.safehood.dto.LoginDTO;
import com.safehood.dto.ResponseDTO;
import com.safehood.model.User;
import com.safehood.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class LoginController {
    
    @Autowired
    private LoginService loginService;
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        if (loginDTO.getUsername() == null || loginDTO.getUsername().isEmpty() || 
            loginDTO.getPassword() == null || loginDTO.getPassword().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDTO("Los campos de usuario y contraseña son requeridos", false));
        }
        
        User user = loginService.validateLogin(loginDTO.getUsername(), loginDTO.getPassword());
        
        if (user != null) {
            return ResponseEntity.ok(new ResponseDTO("Login exitoso", true));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseDTO("Credenciales inválidas", false));
        }
    }

}
