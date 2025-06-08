package com.safehood.controller;

import com.safehood.dto.ProfileDTO;
import com.safehood.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {
    
    @Autowired
    private ProfileService profileService;
    
    @GetMapping
    public ResponseEntity<ProfileDTO> getProfile(@RequestParam String username) {
        return ResponseEntity.ok(profileService.getProfile(username));
    }
    
    @PutMapping
    public ResponseEntity<?> updateProfile(@RequestParam String username, @RequestBody ProfileDTO profileDTO) {
        profileService.updateProfile(username, profileDTO);
        return ResponseEntity.ok("Perfil actualizado exitosamente");
    }
}
