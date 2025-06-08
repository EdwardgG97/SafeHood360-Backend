package com.safehood.service;

import com.safehood.dto.PqrDTO;
import com.safehood.model.Pqr;
import com.safehood.model.User;
import com.safehood.repository.PqrRepository;
import com.safehood.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PqrService {
    
    @Autowired
    private PqrRepository pqrRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public void createPqr(String username, PqrDTO pqrDTO) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        Pqr pqr = new Pqr();
        pqr.setUser(user);
        pqr.setEmail(pqrDTO.getEmail());
        pqr.setMessage(pqrDTO.getMessage());
        pqr.setStatus("Pendiente");
        pqr.setCreatedAt(LocalDateTime.now());
        pqr.setUpdatedAt(LocalDateTime.now());
        
        pqrRepository.save(pqr);
    }
    
    public List<PqrDTO> getUserPqrs(String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        return pqrRepository.findByUser(user)
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    private PqrDTO convertToDTO(Pqr pqr) {
        PqrDTO dto = new PqrDTO();
        dto.setEmail(pqr.getEmail());
        dto.setMessage(pqr.getMessage());
        dto.setStatus(pqr.getStatus());
        dto.setCreatedAt(pqr.getCreatedAt());
        dto.setUpdatedAt(pqr.getUpdatedAt());
        return dto;
    }
}
