package com.safehood.controller;

import com.safehood.dto.PqrDTO;
import com.safehood.service.PqrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pqrs")
public class PqrController {
    
    @Autowired
    private PqrService pqrService;
    
    @PostMapping
    public ResponseEntity<?> createPqr(@RequestParam String username, @RequestBody PqrDTO pqrDTO) {
        pqrService.createPqr(username, pqrDTO);
        return ResponseEntity.ok("PQR creado exitosamente");
    }
    
    @GetMapping
    public ResponseEntity<?> getUserPqrs(@RequestParam String username) {
        return ResponseEntity.ok(pqrService.getUserPqrs(username));
    }
}
