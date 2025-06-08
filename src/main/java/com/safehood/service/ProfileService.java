package com.safehood.service;

import com.safehood.dto.ProfileDTO;
import com.safehood.model.User;
import com.safehood.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    @Autowired
    private UserRepository userRepository;

    public ProfileDTO getProfile(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> 
            new RuntimeException("Usuario no encontrado"));
        
        ProfileDTO profile = new ProfileDTO();
        profile.setFirstName(user.getFirstName());
        profile.setLastName(user.getLastName());
        profile.setGender(user.getGender());
        profile.setAddress(user.getAddress());
        profile.setEmail(user.getEmail());
        profile.setPhone(user.getPhone());
        profile.setBirthDate(user.getBirthDate());
        profile.setProfileImage(user.getProfileImage());
        
        return profile;
    }

    public void updateProfile(String username, ProfileDTO profileDTO) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> 
            new RuntimeException("Usuario no encontrado"));
        
        user.setFirstName(profileDTO.getFirstName());
        user.setLastName(profileDTO.getLastName());
        user.setGender(profileDTO.getGender());
        user.setAddress(profileDTO.getAddress());
        user.setPhone(profileDTO.getPhone());
        user.setBirthDate(profileDTO.getBirthDate());
        user.setProfileImage(profileDTO.getProfileImage());
        
        userRepository.save(user);
    }
}
