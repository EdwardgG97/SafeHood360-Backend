package com.safehood.service;

import com.safehood.model.User;
import com.safehood.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    
    @Autowired
    private UserRepository userRepository;
    
    public User validateLogin(String username, String password) {
        return userRepository.findByUsername(username)
            .filter(user -> user.getPassword().equals(password))
            .orElse(null);
    }
}
