package com.safehood.service;

import com.safehood.dto.RegistrationDTO;
import com.safehood.model.User;
import com.safehood.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {
    
    @Autowired
    private UserRepository userRepository;
    
    public void register(RegistrationDTO registrationDTO) {
        // Verificar si el email ya existe
        if (userRepository.findByEmail(registrationDTO.getEmail()).isPresent()) {
            throw new RuntimeException("El correo electrónico ya está registrado");
        }
        
        // Crear nuevo usuario
        User user = new User();
        user.setUsername(registrationDTO.getFirstName());
        user.setFirstName(registrationDTO.getFirstName());
        user.setLastName(registrationDTO.getLastName());
        user.setEmail(registrationDTO.getEmail());
        user.setPassword(registrationDTO.getPassword()); // Sin codificación
        user.setAddress(registrationDTO.getAddress());
        user.setPhone(registrationDTO.getPhone());
        user.setBirthDate(registrationDTO.getBirthDate());
        user.setGender(registrationDTO.getGender());
        user.setBarrio1(registrationDTO.getBarrio1());
        user.setBarrio2(registrationDTO.getBarrio2());
        user.setBarrio3(registrationDTO.getBarrio3());
        user.setProfileImage(registrationDTO.getProfileImage());
        
        // Guardar usuario
        userRepository.save(user);
    }
}
